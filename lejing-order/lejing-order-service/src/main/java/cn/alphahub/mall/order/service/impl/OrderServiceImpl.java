package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.constant.CartConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.cart.vo.CartItemVo;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.MemberAddressVo;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.exception.BizException;
import cn.alphahub.mall.order.feign.MemberReceiveAddressClient;
import cn.alphahub.mall.order.feign.SkuInfoClient;
import cn.alphahub.mall.order.interceptor.LoginInterceptor;
import cn.alphahub.mall.order.mapper.OrderMapper;
import cn.alphahub.mall.order.service.OrderService;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 订单Service业务层处理
 *
 * @author Weasley J
 * @date 2021-02-24 16:02:31
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private SkuInfoClient skuInfoClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MemberReceiveAddressClient memberReceiveAddressClient;

    /**
     * 查询订单分页列表
     *
     * @param pageDomain 分页数据
     * @param order      分页对象
     * @return 订单分页数据
     */
    @Override
    public PageResult<Order> queryPage(PageDomain pageDomain, Order order) {
        PageResult<Order> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<Order> orderList = list(new QueryWrapper<>(order));
        return pageResult.getPage(orderList);
    }

    /**
     * 去结算确认页
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        OrderConfirmVo vo = new OrderConfirmVo();

        //从拦截器中获取当前用户信息
        Member member = LoginInterceptor.getUserInfo();

        log.info("开始远程查询查询用户【{}】的收货地址列表:{}", member.getId(), JSONUtil.toJsonStr(member));
        BaseResult<List<MemberReceiveAddress>> result = memberReceiveAddressClient.memberAddressList(member.getId());
        if (result.getSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
            List<MemberAddressVo> vos = result.getData().stream().map(memberReceiveAddress -> {
                MemberAddressVo addressVo = MemberAddressVo.builder().build();
                BeanUtils.copyProperties(memberReceiveAddress, addressVo);
                return addressVo;
            }).collect(Collectors.toList());
            vo.setMemberAddressVos(vos);
        }

        log.info("远程查询用户【{}】购物中的所有购物项...", member.getId());
        /*List<CartItemVo> itemVos = cartClient.getCurrentCartItems();
        if (CollectionUtils.isNotEmpty(itemVos)) {
            List<OrderItemVo> orderItemVos = itemVos.stream().map(cartItemVo -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                BeanUtils.copyProperties(cartItemVo, orderItemVo);
                return orderItemVo;
            }).collect(Collectors.toList());
            vo.setItems(orderItemVos);
        }*/

        // 设置会员积分
        vo.setIntegration(member.getIntegration());

        return vo;
    }

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    @Override
    public List<CartItemVo> getUserCartItems() {
        List<CartItemVo> items = Lists.newArrayList();
        Member userInfo = LoginInterceptor.getUserInfo();
        if (Objects.isNull(userInfo.getId())) {
            return items;
        }

        String cartKey = CartConstant.CART_PREFIX + userInfo.getId();
        List<CartItemVo> cartItems = getCartItems(cartKey);
        if (CollectionUtils.isEmpty(cartItems)) {
            log.warn("用户的购物车为空!");
            throw new BizException("用户的购物车为空!");
        }

        // 使用多线程 - 筛选出默认被选中的sku
        CompletableFuture<List<CartItemVo>> queryLatestPriceFuture = CompletableFuture.supplyAsync(() -> {
            List<CartItemVo> itemVos = cartItems.stream()
                    .filter(CartItemVo::getCheck)
                    .peek(item -> {
                        // 远程查询商品的最新价格
                        BaseResult<SkuInfo> result = skuInfoClient.info(item.getSkuId());
                        log.info("远程查询最新的商品价格, 结果:{}", JSONUtil.toJsonPrettyStr(result));
                        if (result.getSuccess()) {
                            SkuInfo skuInfo = result.getData();
                            item.setPrice(skuInfo.getPrice());
                        }
                    }).collect(Collectors.toList());
            items.addAll(itemVos);
            return itemVos;
        }, executor);

        // 使用多线程异步任务编排 - 等待所有任务执行完才返回数据
        try {
            CompletableFuture.allOf(queryLatestPriceFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("所有多线程异步任务执行任务出错，异常原因：{}", e.getLocalizedMessage(), e);
        }
        return items;
    }

    /**
     * 获取临时购物车里面的数据
     *
     * @param cartKey redis中购物车的key
     * @return 临时购物车的数据
     */
    private List<CartItemVo> getCartItems(String cartKey) {
        // 获取购物车里面的所有商品
        BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps(cartKey);
        List<Object> tempCartValues = operations.values();
        if (CollectionUtils.isNotEmpty(tempCartValues)) {
            return tempCartValues.stream().map(obj -> JSONUtil.toBean(String.valueOf(obj), CartItemVo.class)).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
