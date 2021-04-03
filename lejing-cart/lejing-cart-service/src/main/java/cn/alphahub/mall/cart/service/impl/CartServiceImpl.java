package cn.alphahub.mall.cart.service.impl;

import cn.alphahub.mall.cart.domain.Cart;
import cn.alphahub.mall.cart.service.CartService;
import cn.alphahub.mall.cart.vo.CartItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 将商品添加至购物车
     *
     * @param skuId sku id
     * @param num   数量
     * @return 购物项内容
     */
    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        return null;
    }

    /**
     * 获取购物车某个购物项
     *
     * @param skuId sku id
     * @return 购物项内容
     */
    @Override
    public CartItemVo getCartItem(Long skuId) {
        return null;
    }

    /**
     * 获取购物车里面的信息
     *
     * @return 整个购物车存放的商品信息
     */
    @Override
    public Cart getCart() throws ExecutionException, InterruptedException {
        return null;
    }

    /**
     * 清空购物车的数据
     *
     * @param cartKey 购物车的key
     */
    @Override
    public void clearCartInfo(String cartKey) {

    }

    /**
     * 勾选购物项
     *
     * @param skuId sku id
     * @param check 是否选中
     */
    @Override
    public void checkItem(Long skuId, Integer check) {

    }

    /**
     * 改变商品数量
     *
     * @param skuId sku id
     * @param num   数量
     */
    @Override
    public void changeItemCount(Long skuId, Integer num) {

    }

    /**
     * 删除购物项
     *
     * @param skuId sku id
     */
    @Override
    public void deleteIdCartInfo(Integer skuId) {

    }

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    @Override
    public List<CartItemVo> getUserCartItems() {
        return null;
    }
}
