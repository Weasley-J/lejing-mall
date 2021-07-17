package cn.alphahub.mall.cart.service.impl;

import cn.alphahub.common.constant.CartConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.to.UserInfoTo;
import cn.alphahub.mall.cart.domain.Cart;
import cn.alphahub.mall.cart.exception.CartException;
import cn.alphahub.mall.cart.feign.SkuInfoClient;
import cn.alphahub.mall.cart.feign.SkuSaleAttrValueClient;
import cn.alphahub.mall.cart.interceptor.CartInterceptor;
import cn.alphahub.mall.cart.service.CartService;
import cn.alphahub.mall.cart.vo.CartItemVo;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <b>购物车业务实现</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SkuInfoClient skuInfoClient;
    @Resource
    private SkuSaleAttrValueClient attrValueClient;

    /**
     * 将商品添加至购物车
     *
     * @param skuId 商品sku id
     * @param num   购物车商品数量
     * @return 购物项内容
     */
    @Override
    public CartItemVo addCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        CartItemVo cartItem = new CartItemVo();
        cartItem.setSkuId(skuId);
        cartItem.setCount(num);

        BoundHashOperations<String, Object, Object> cartOps = this.getCartOps();

        // redis已经有了的话,只修改数量
        Object skuResult = cartOps.get(skuId.toString());
        if (Objects.nonNull(skuResult)) {
            return updateCart(CartItemVo.builder().skuId(skuId).count(num).build());
        }

        // 使用线程池: 远程查询商品服务获取sku信息
        CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
            BaseResult<SkuInfo> result = skuInfoClient.info(skuId);
            log.info("远程查询:\n{}", JSONUtil.toJsonStr(result));
            if (result.getSuccess()) {
                SkuInfo skuInfo = result.getData();
                log.info("远程查询商品sku信息成功: \n{}", JSONUtil.toJsonPrettyStr(skuInfo));
                // 将商品sku信息添加到购物车
                cartItem.setCheck(Boolean.TRUE);
                cartItem.setTitle(skuInfo.getSkuTitle());
                cartItem.setImage(skuInfo.getSkuDefaultImg());
                cartItem.setPrice(skuInfo.getPrice());
            }
        }, executor);

        // 使用线程池: 远程查询商品服务获取商品sku属性列表
        CompletableFuture<Void> skuAttrValueFuture = CompletableFuture.runAsync(() -> {
            BaseResult<List<String>> result = attrValueClient.getSkuAttrValues(skuId);
            log.info("远程查询结果:\n{}", JSONUtil.toJsonStr(result));
            if (result.getSuccess()) {
                List<String> skuAttrValues = result.getData();
                log.info("远程查询商品sku属性列表成功: {}", JSONUtil.toJsonPrettyStr(skuAttrValues));
                cartItem.setSkuAttrValues(skuAttrValues);
            }
        }, executor);

        // 等待线程完成
        CompletableFuture.allOf(skuInfoFuture, skuAttrValueFuture).get();

        // 将购物车数据存入redis中
        cartOps.put(String.valueOf(skuId), JSONUtil.toJsonStr(cartItem));
        return cartItem;
    }

    /**
     * 删除购物车的商品
     *
     * @param skuId 商品skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = this.getCartOps();
        cartOps.delete(String.valueOf(skuId));
    }

    /**
     * 修改购物车的商品的数量
     *
     * @param cartItem 购物车实体类
     */
    public CartItemVo updateCart(CartItemVo cartItem) {
        BoundHashOperations<String, Object, Object> ops = this.getCartOps();
        try {
            Long skuId = cartItem.getSkuId();
            String skuIdString = String.valueOf(skuId);
            Object obj = ops.get(skuIdString);
            if (Objects.nonNull(obj)) {
                // 获取购物车信息
                CartItemVo oldCartItem = objectMapper.readValue(obj.toString(), CartItemVo.class);
                // 更新数量
                oldCartItem.setCount(oldCartItem.getCount() + cartItem.getCount());
                oldCartItem.setTotalPrice(NumberUtil.add(oldCartItem.getTotalPrice(), cartItem.getTotalPrice()));
                // 写入购物车
                ops.put(skuIdString, objectMapper.writeValueAsString(oldCartItem));
                cartItem = oldCartItem;
            }
        } catch (JsonProcessingException e) {
            log.error("json解析异常：{}\n", e.getMessage(), e);
        }
        return cartItem;
    }

    /**
     * 根据用户信息获取Redis中的购物车数据
     *
     * @return Redis操作购物车的实例
     */
    public BoundHashOperations<String, Object, Object> getCartOps() {
        // 从 thread local 获取用户信息
        UserInfoTo userInfo = CartInterceptor.getUserInfo();
        Long userId = userInfo.getUserId();
        // 用户已登录|用户未登录
        String cartKey = Objects.nonNull(userId) ? CartConstant.CART_PREFIX + userId : CartConstant.CART_PREFIX + userInfo.getUserKey();
        return stringRedisTemplate.boundHashOps(cartKey);
    }

    /**
     * 获取购物车某个购物项
     *
     * @param skuId 商品skuId
     * @return 购物项内容
     */
    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = this.getCartOps();
        Object skuString = cartOps.get(String.valueOf(skuId));
        log.info("从redis中获取购物车中 {} 购物项信息:{}", skuId, JSONUtil.toJsonPrettyStr(skuString));
        return JSONUtil.toBean(JSONUtil.toJsonStr(skuString), CartItemVo.class);
    }

    /**
     * 获取购物车里面的信息
     *
     * @return 整个购物车存放的商品信息
     */
    @Override
    public Cart getCart() throws ExecutionException, InterruptedException {
        Cart cart = new Cart();
        // 从 thread local 获取用户信息
        UserInfoTo userInfo = CartInterceptor.getUserInfo();
        Long userId = userInfo.getUserId();
        BoundHashOperations<String, Object, Object> ops = null;
        String cartKey;
        // 1. 用户已登录
        if (Objects.nonNull(userId)) {
            cartKey = CartConstant.CART_PREFIX + userId;
            String tempCartKey = CartConstant.CART_PREFIX + userInfo.getUserKey();
            // 1.1 判断下临时购物车中有没有数据
            List<CartItemVo> tempCartItems = getCartItems(tempCartKey);
            if (CollectionUtils.isNotEmpty(tempCartItems)) {
                // 合并临时购物车中的数据
                for (CartItemVo cartItem : tempCartItems) {
                    this.addCart(cartItem.getSkuId(), cartItem.getCount());
                }
                // 清空购物车的数据
                clearCartInfo(tempCartKey);
            }
            // 1.2 获取登录后的购物商品sku数据(包含合并后的临时购物车的数据)
            List<CartItemVo> cartItems = getCartItems(cartKey);
            cart.setItems(cartItems);
        }
        // 2. 用户未登录
        if (userInfo.getTempUser()) {
            cartKey = CartConstant.CART_PREFIX + userInfo.getUserKey();
            ops = stringRedisTemplate.boundHashOps(cartKey);
            List<Object> values = ops.values();
            if (CollectionUtils.isNotEmpty(values)) {
                List<CartItemVo> itemVos = new ArrayList<>();
                for (Object o : values) {
                    CartItemVo cartItemVo = JSONUtil.toBean(o.toString(), CartItemVo.class);
                    itemVos.add(cartItemVo);
                }
                cart.setItems(itemVos);
            }
        }
        return cart.buildCartMetaData(cart);
    }

    /**
     * 清空购物车的数据
     *
     * @param cartKey 购物车的key
     * @return 清除成功|失败
     */
    @Override
    public Boolean clearCartInfo(String cartKey) {
        return stringRedisTemplate.delete(cartKey);
    }

    /**
     * 勾选购物项
     *
     * @param skuId 商品skuId
     * @param check 是否选中
     */
    @Override
    public void checkItem(Long skuId, Integer check) {
        CartItemVo cartItem = getCartItem(skuId);
        String jsonStrBefore = JSONUtil.toJsonStr(cartItem);
        cartItem.setCheck(Objects.equals(check, 1));
        String jsonStrAfter = JSONUtil.toJsonStr(cartItem);
        // 修改redis中的购物项
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        ops.put(skuId.toString(), jsonStrAfter);
        log.info("修改前：{}, 修改后: {}", jsonStrBefore, jsonStrAfter);
    }

    /**
     * 修改购物车中商品数量
     *
     * @param skuId 商品skuId
     * @param num   数量
     */
    @Override
    public void changeItemCount(Long skuId, Integer num) {
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(String.valueOf(skuId), JSONUtil.toJsonStr(cartItem));
    }

    /**
     * 删除redis中用户的购物项
     *
     * @param skuId 商品skuId
     */
    @Override
    public void deleteIdCartInfo(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(String.valueOf(skuId));
    }

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    @Override
    public List<CartItemVo> getUserCartItems() {
        List<CartItemVo> items = Lists.newArrayList();
        UserInfoTo userInfo = CartInterceptor.getUserInfo();
        if (Objects.isNull(userInfo.getUserId())) {
            return items;
        }

        String cartKey = CartConstant.CART_PREFIX + userInfo.getUserId();
        List<CartItemVo> cartItems = getCartItems(cartKey);
        if (CollectionUtils.isEmpty(cartItems)) {
            log.warn("用户的购物车为空!");
            throw new CartException("用户的购物车为空!");
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
            Thread.currentThread().interrupt();
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
            List<CartItemVo> list = new ArrayList<>();
            for (Object obj : tempCartValues) {
                CartItemVo cartItemVo = JSONUtil.toBean(String.valueOf(obj), CartItemVo.class);
                list.add(cartItemVo);
            }
            return list;
        }
        return Lists.newArrayList();
    }
}
