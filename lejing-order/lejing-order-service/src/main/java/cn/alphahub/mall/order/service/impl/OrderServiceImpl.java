package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.constant.CartConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.mall.cart.vo.CartItemVo;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.order.constant.OrderConstant;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.dto.to.OrderCreateTo;
import cn.alphahub.mall.order.dto.vo.FareVo;
import cn.alphahub.mall.order.dto.vo.MemberAddressVo;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.dto.vo.OrderItemVo;
import cn.alphahub.mall.order.dto.vo.OrderSubmitVo;
import cn.alphahub.mall.order.dto.vo.SubmitOrderResponseVo;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.order.feign.BrandClient;
import cn.alphahub.mall.order.feign.CartClient;
import cn.alphahub.mall.order.feign.MemberReceiveAddressClient;
import cn.alphahub.mall.order.feign.SkuInfoClient;
import cn.alphahub.mall.order.feign.SpuInfoClient;
import cn.alphahub.mall.order.feign.WareInfoClient;
import cn.alphahub.mall.order.feign.WareSkuClient;
import cn.alphahub.mall.order.interceptor.LoginInterceptor;
import cn.alphahub.mall.order.mapper.OrderMapper;
import cn.alphahub.mall.order.service.OrderItemService;
import cn.alphahub.mall.order.service.OrderService;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.Failable;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.alphahub.mall.order.constant.OrderConstant.OrderStatusEnum;

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
    public SpuInfoClient spuInfoClient;
    @Resource
    public BrandClient brandClient;
    @Resource
    private OrderItemService orderItemService;
    /**
     * 共享订单数据
     */
    private ThreadLocal<OrderSubmitVo> confirmVoThreadLocal = new ThreadLocal<>();
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private SkuInfoClient skuInfoClient;
    @Resource
    private CartClient cartClient;
    @Resource
    private WareSkuClient wareSkuClient;
    @Resource
    private WareInfoClient wareInfoClient;
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
     * 订单结算确认页
     * <ul>
     *     <li>使用CompletableFuture进行异步任务编排时让所有线程共享一个requestAttributes（{@code RequestContextHolder.getRequestAttributes()}）中获取</li>
     *     <li>使用CompletableFuture进行异步任务编排，让所有Feign远程查询 一起执行</li>
     * </ul>
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        Member member = LoginInterceptor.getUserInfo();
        confirmVo.setIntegration(ObjectUtils.defaultIfNull(member.getIntegration(), 0));

        RequestAttributes mainThreadRequestAttributes = RequestContextHolder.getRequestAttributes();
        log.info("线程mainThread,当前线程Id:{},当前线程Name:{}", Thread.currentThread().getId(), Thread.currentThread().getName());

        CompletableFuture<Void> addressListFuture = CompletableFuture.runAsync(() -> {
            log.info("异步线程addressListFuture,当前线程Id:{},当前线程Name:{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            log.info("开始远程查询查询用户[{}]的收货地址列表:{}", member.getId(), JSONUtil.toJsonStr(member));
            RequestContextHolder.setRequestAttributes(mainThreadRequestAttributes);
            List<MemberAddressVo> addressVos;
            BaseResult<List<MemberReceiveAddress>> result = memberReceiveAddressClient.memberAddressList(member.getId());
            if (result.getSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
                addressVos = result.getData().stream().map(memberReceiveAddress -> {
                    MemberAddressVo addressVo = new MemberAddressVo();
                    BeanUtils.copyProperties(memberReceiveAddress, addressVo);
                    return addressVo;
                }).collect(Collectors.toList());
                confirmVo.setMemberAddressVos(addressVos);
            }
        }, executor);

        CompletableFuture<Void> cartItemFuture = CompletableFuture.runAsync(() -> {
            log.info("异步线程cartItemFuture,当前线程Id:{},当前线程Name:{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            log.info("远程查询用户[{}]购物中的所有购物项...", member.getId());
            RequestContextHolder.setRequestAttributes(mainThreadRequestAttributes);
            List<OrderItemVo> itemVos = Failable.apply(input -> input.stream().map(cartItem -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                BeanUtils.copyProperties(cartItem, orderItemVo);
                return orderItemVo;
            }).collect(Collectors.toList()), cartClient.getCurrentCartItems());
            confirmVo.setItems(itemVos);
        }, executor).thenRunAsync(() -> {
            List<Long> skuIds = confirmVo.getItems().stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            BaseResult<List<WareSkuVO>> result = wareSkuClient.getSkuHasStock(skuIds);
            log.info("远程查询用户[{}]购物中商品的库存信息,响应:{}", member.getId(), JSONUtil.toJsonPrettyStr(result));
            if (result.getSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
                Map<Long, Boolean> skuHasStockMap = result.getData().stream().collect(Collectors.toMap(WareSkuVO::getSkuId, WareSkuVO::getHasStock));
                confirmVo.setStocks(skuHasStockMap);
            }
        }, executor);

        //接口幂等性-防重复提交
        String token = IdUtil.fastSimpleUUID();
        stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_CONFIRM_TOKEN + member.getId(), token, 15, TimeUnit.MINUTES);
        confirmVo.setOrderToken(token);

        try {
            CompletableFuture.allOf(addressListFuture, cartItemFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("订单结算确认多线程异步任务执行任务出错,异常原因:{}", e.getMessage(), e);
        }

        confirmVoThreadLocal.remove();

        return confirmVo;
    }

    /**
     * 查询当前登录用户购物项内容列表
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
     * 提交订单结算 - 下单功能
     * <p>下单：创建订单 、验证令牌、验证价格、锁定库存</p>
     *
     * @param submitVo 订单提交数据
     * @return 提交订单响应数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {

        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        responseVo.setCode(0);

        confirmVoThreadLocal.set(submitVo);
        Member member = LoginInterceptor.getUserInfo();
        String token = submitVo.getOrderToken();
        String key = OrderConstant.USER_ORDER_CONFIRM_TOKEN + member.getId();

        //1、验证令牌是否合法【令牌的对比和删除必须保证原子性】, rua脚本执行结果: 1L 验证成功；0L 验证失败
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Lists.newArrayList(key), token);

        if (Objects.equals(result, 0L)) {
            log.info("令牌验证不通过：{}", JSONUtil.toJsonPrettyStr(submitVo));
            responseVo.setCode(1);
            return responseVo;
        }

        if (Objects.equals(result, 1L)) {
            log.info("令牌验证通过：{}", JSONUtil.toJsonPrettyStr(submitVo));
            // 创建订单项
            OrderCreateTo to = createOrder();
            // 验价
            BigDecimal payAmount = to.getOrder().getPayAmount();
            if (CompareUtil.compare(submitVo.getPayPrice().subtract(payAmount).abs(), OrderConstant.RMB_MIN_UNIT) < 0) {
                log.info("验价成功.");
                // 保存订单数据
                saveOrder(to);
                // 锁定库存, 只要有异常就回滚订单数据, 订单号,所有订单项数据(skuId,skuName,num)
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(to.getOrder().getOrderSn());
                List<OrderItemVo> itemVos = to.getOrderItems().stream().map(orderItem -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    orderItemVo.setSkuId(orderItem.getSkuId());
                    orderItemVo.setTitle(orderItem.getSkuName());
                    orderItemVo.setImage(orderItem.getSkuPic());
                    orderItemVo.setSkuAttrValues(Lists.newArrayList(orderItem.getSkuAttrsVals().split(";")));
                    orderItemVo.setPrice(orderItem.getSkuPrice());
                    orderItemVo.setCount(orderItem.getSkuQuantity());
                    orderItemVo.setTotalPrice(orderItem.getRealAmount());
                    return orderItemVo;
                }).collect(Collectors.toList());
                lockVo.setLocks(itemVos);

                BaseResult<LockStockResultTo> baseResult = wareSkuClient.orderLockStock(lockVo);
                if (baseResult.getSuccess() && baseResult.getData().getIsAllSkuLocked()) {
                    log.info("锁库存成功");
                    responseVo.setOrder(to.getOrder());
                } else {
                    log.warn("锁库存失败：{}", JSONUtil.toJsonStr(baseResult));
                    responseVo.setCode(3);
                    responseVo.setMsg(baseResult.getMessage());
                }
            } else {
                log.info("验价失败!");
                responseVo.setCode(2);
                return responseVo;
            }
        }

        return responseVo;
    }

    /**
     * 保存订单数据
     *
     * @param to 创建订单的数据
     */
    private void saveOrder(OrderCreateTo to) {
        Date now = new Date();
        Order order = to.getOrder();
        order.setCreateTime(now);
        order.setModifyTime(now);
        save(order);
        List<OrderItem> orderItems = to.getOrderItems();
        orderItemService.saveBatch(orderItems);
    }

    /**
     * 创建订单
     *
     * @return 订单数据
     */
    private OrderCreateTo createOrder() {
        OrderCreateTo createTo = new OrderCreateTo();
        // 所有订单项数据
        createTo.setOrder(buildOrder());
        createTo.setOrderItems(buildOrderItems(createTo.getOrder().getOrderSn()));
        computePrice(createTo.getOrder(), createTo.getOrderItems());
        createTo.setPayPrice(createTo.getOrder().getPayAmount());
        createTo.setFare(createTo.getOrder().getFreightAmount());
        return createTo;
    }

    /**
     * 计算价格
     *
     * @param order      订单
     * @param orderItems 订单项列表
     */
    private void computePrice(Order order, List<OrderItem> orderItems) {
        // 总金额=每一个订单项的总额叠加
        BigDecimal totalPrice = BigDecimal.ZERO;

        BigDecimal promotionDiscountPrice = BigDecimal.ZERO;
        BigDecimal couponDiscountPrice = BigDecimal.ZERO;
        BigDecimal integrationDiscountPrice = BigDecimal.ZERO;

        Integer giftGrowth = 0;
        Integer integration = 0;
        for (OrderItem item : orderItems) {
            promotionDiscountPrice = promotionDiscountPrice.add(item.getPromotionAmount());
            couponDiscountPrice = couponDiscountPrice.add(item.getCouponAmount());
            integrationDiscountPrice = integrationDiscountPrice.add(item.getIntegrationAmount());
            totalPrice = totalPrice.add(item.getRealAmount());
            giftGrowth += item.getGiftGrowth();
            integration += item.getGiftIntegration();
        }
        // 促销优慧金额
        order.setPromotionAmount(promotionDiscountPrice);
        // 优惠券抵扣金额
        order.setCouponAmount(couponDiscountPrice);
        // 积分抵扣金额
        order.setIntegrationAmount(integrationDiscountPrice);
        // 订单总额
        order.setTotalAmount(totalPrice);
        // 应付总额
        order.setPayAmount(totalPrice.add(order.getFreightAmount()).setScale(2, RoundingMode.DOWN));
        order.setGrowth(giftGrowth);
        order.setIntegration(integration);
        order.setDeleteStatus(0);
    }

    /**
     * 构建订单
     *
     * @return 订单
     */
    private Order buildOrder() {
        log.info("构建订单...");
        Order order = new Order();
        // 订单号
        String orderSn = IdWorker.getTimeId();
        order.setOrderSn(orderSn);
        order.setStatus(OrderStatusEnum.CREATE_NEW.getValue());
        order.setAutoConfirmDay(7);
        order.setMemberId(LoginInterceptor.getUserInfo().getId());
        order.setMemberUsername(LoginInterceptor.getUserInfo().getUsername());
        // 收货人信息+运费
        OrderSubmitVo submitVo = confirmVoThreadLocal.get();
        BaseResult<FareVo> postageInfo = wareInfoClient.getPostageInfo(submitVo.getAddrId());
        FareVo data = postageInfo.getData();
        if (postageInfo.getSuccess() && Objects.nonNull(data)) {
            MemberAddressVo address = data.getAddress();
            order.setReceiverName(address.getName());
            order.setReceiverPhone(address.getPhone());
            order.setReceiverPostCode(address.getPostCode());
            order.setReceiverProvince(address.getProvince());
            order.setReceiverCity(address.getCity());
            order.setReceiverRegion(address.getRegion());
            order.setReceiverDetailAddress(address.getDetailAddress());
            order.setFreightAmount(data.getFare());
        }
        return order;
    }

    /**
     * 构建订单项数据列表
     *
     * @return 订单项信息列表
     */
    private List<OrderItem> buildOrderItems(String orderSn) {
        log.info("构建订单项数据列表...");
        List<CartItemVo> userCartItems = getUserCartItems();
        if (CollectionUtils.isEmpty(userCartItems)) {
            return null;
        }
        return userCartItems.stream().map(cartItemVo -> {
            OrderItem orderItem = buildOrderItem(cartItemVo);
            assert orderItem != null;
            orderItem.setOrderSn(orderSn);
            return orderItem;
        }).collect(Collectors.toList());
    }

    /**
     * 构建某一个订单项数据列表
     * <ul>
     *     <p>构建的数据项</p>
     *     <li>订单数据、订单号</li>
     *     <li>商品SPU信息</li>
     *     <li>商品SKU信息</li>
     *     <li>优惠信息<em>（不做)</em></li>
     *     <li>积分信息</li>
     * </ul>
     *
     * @param cartItemVo 购物项内容
     * @return 订单项信息
     */
    private OrderItem buildOrderItem(CartItemVo cartItemVo) {
        log.info("构建某一个订单项数据列表:{}", JSONUtil.toJsonPrettyStr(cartItemVo));
        if (Objects.isNull(cartItemVo)) {
            return null;
        }
        OrderItem orderItem = new OrderItem();

        // TODO 使用多线程查询
        Long skuId = cartItemVo.getSkuId();
        BaseResult<SkuInfo> skuInfoBaseResult = skuInfoClient.info(skuId);
        SkuInfo skuInfo = skuInfoBaseResult.getData();
        if (skuInfoBaseResult.getSuccess() && Objects.nonNull(skuInfo)) {
            orderItem.setSpuId(skuInfo.getSpuId());
            orderItem.setSpuPic(skuInfo.getSkuDefaultImg());
            // 查SPU信息
            BaseResult<SpuInfo> spuInfoBaseResult = spuInfoClient.info(skuInfo.getSpuId());
            SpuInfo spuInfo = spuInfoBaseResult.getData();
            if (spuInfoBaseResult.getSuccess() && Objects.nonNull(spuInfo)) {
                orderItem.setSpuName(spuInfo.getSpuName());
                orderItem.setCategoryId(spuInfo.getCatalogId());
                //  查品牌
                BaseResult<Brand> brandBaseResult = brandClient.info(skuInfo.getBrandId());
                if (brandBaseResult.getSuccess() && Objects.nonNull(brandBaseResult.getData())) {
                    orderItem.setSpuBrand(brandBaseResult.getData().getName());
                }
            }
        }

        // SKU
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(cartItemVo.getTitle());
        orderItem.setSkuPic(cartItemVo.getImage());
        orderItem.setSkuPrice(cartItemVo.getPrice());
        orderItem.setSkuQuantity(cartItemVo.getCount());
        orderItem.setSkuAttrsVals(StringUtils.join(cartItemVo.getSkuAttrValues(), ";"));

        //优惠信息（不做)

        // 设置优惠价格
        orderItem.setPromotionAmount(BigDecimal.ZERO);
        orderItem.setCouponAmount(BigDecimal.ZERO);
        orderItem.setIntegrationAmount(BigDecimal.ZERO);

        BigDecimal originAmount = NumberUtil.mul(orderItem.getSkuPrice(), new BigDecimal("" + orderItem.getSkuQuantity()));
        originAmount = NumberUtil.sub(originAmount, orderItem.getPromotionAmount(), orderItem.getCouponAmount(), orderItem.getIntegrationAmount());
        // 最终付款金额
        orderItem.setRealAmount(originAmount);

        orderItem.setGiftGrowth(cartItemVo.getPrice().intValue() * cartItemVo.getCount());
        orderItem.setGiftIntegration(cartItemVo.getPrice().intValue() * cartItemVo.getCount());
        return orderItem;
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
