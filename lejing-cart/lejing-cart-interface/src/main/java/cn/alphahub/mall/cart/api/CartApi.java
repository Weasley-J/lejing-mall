package cn.alphahub.mall.cart.api;

import cn.alphahub.mall.cart.vo.CartItemVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 购物车 - rpc接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/30
 */
public interface CartApi {

    /**
     * 获取当前用户的购物车商品项
     *
     * @return 用户当前的购物项
     */
    @ResponseBody
    @GetMapping(value = "/currentUserCartItems")
    List<CartItemVo> getCurrentCartItems();
}
