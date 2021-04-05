package cn.alphahub.mall.cart.service;

import cn.alphahub.mall.cart.domain.Cart;
import cn.alphahub.mall.cart.vo.CartItemVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 购物车接口
 *
 * @author liuwenjing
 * @date 2021年4月3日
 */
public interface CartService {

    /**
     * 将商品添加至购物车
     *
     * @param skuId 商品sku id
     * @param num   购物车商品数量
     * @return 购物项内容
     */
    CartItemVo addCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 删除购物车的商品
     *
     * @param skuId sku id
     */
    void deleteCart(Long skuId);

    /**
     * 获取购物车某个购物项
     *
     * @param skuId sku id
     * @return 购物项内容
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 获取购物车里面的信息
     *
     * @return 整个购物车存放的商品信息
     */
    Cart getCart() throws ExecutionException, InterruptedException;

    /**
     * 清空购物车的数据
     *
     * @param cartKey 购物车的key
     */
    Boolean clearCartInfo(String cartKey);

    /**
     * 勾选购物项
     *
     * @param skuId sku id
     * @param check 是否选中
     */
    void checkItem(Long skuId, Integer check);

    /**
     * 改变商品数量
     *
     * @param skuId sku id
     * @param num   数量
     */
    void changeItemCount(Long skuId, Integer num);


    /**
     * 删除购物项
     *
     * @param skuId sku id
     */
    void deleteIdCartInfo(Integer skuId);

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    List<CartItemVo> getUserCartItems();
}
