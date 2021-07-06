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
     * @param skuId 商品skuId
     * @param num   购物车商品数量
     * @return 购物项内容
     * @throws ExecutionException
     * @throws InterruptedException
     */
    CartItemVo addCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 删除购物车的商品
     *
     * @param skuId 商品skuId
     */
    void deleteCart(Long skuId);

    /**
     * 获取购物车某个购物项
     *
     * @param skuId 商品skuId
     * @return 购物项内容
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 获取购物车里面的信息
     *
     * @return 整个购物车存放的商品信息
     * @throws ExecutionException
     * @throws InterruptedException
     */
    Cart getCart() throws ExecutionException, InterruptedException;

    /**
     * 清空购物车的数据
     *
     * @param cartKey 购物车的key
     * @return 清除成功|失败
     */
    Boolean clearCartInfo(String cartKey);

    /**
     * 勾选购物项
     *
     * @param skuId 商品skuId
     * @param check 选中状态：1 选中， 0 未选中
     */
    void checkItem(Long skuId, Integer check);

    /**
     * 修改购物车中商品数量
     *
     * @param skuId 商品skuId
     * @param num   数量
     */
    void changeItemCount(Long skuId, Integer num);


    /**
     * 删除redis中用户的购物项
     *
     * @param skuId 商品skuId
     */
    void deleteIdCartInfo(Long skuId);

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    List<CartItemVo> getUserCartItems();
}
