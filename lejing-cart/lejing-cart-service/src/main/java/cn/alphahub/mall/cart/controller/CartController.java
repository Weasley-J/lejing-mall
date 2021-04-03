package cn.alphahub.mall.cart.controller;

import cn.alphahub.mall.cart.domain.Cart;
import cn.alphahub.mall.cart.interceptor.CartInterceptor;
import cn.alphahub.mall.cart.service.CartService;
import cn.alphahub.mall.cart.to.UserInfoTo;
import cn.alphahub.mall.cart.vo.CartItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <b>购物车Controller</b>
 *
 * @author Weasley J
 * @version 1.0
 * @date 2021/04/03
 */
@Controller
public class CartController {
    private static final String LEJING_CART_HTML = "http://cart.lejing.com/cart.html";
    private static final String LEJING_CART_HOME = "http://cart.lejing.com";
    @Resource
    private CartService cartService;

    /**
     * 获取当前用户的购物车商品项
     *
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    @ResponseBody
    public List<CartItemVo> getCurrentCartItems() {
        return cartService.getUserCartItems();
    }

    /**
     * 去购物车页面的请求
     * 浏览器有一个cookie:user-key 标识用户的身份，一个月过期
     * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份:
     * 浏览器以后保存，每次访问都会带上这个cookie；
     * <p>
     * 登录：session有
     * 没登录：按照cookie里面带来user-key来做
     * 第一次，如果没有临时用户，自动创建一个临时用户
     *
     * @return cart list
     */
    @GetMapping(value = "/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        //快速得到用户信息：id, user-key
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }


    /**
     * 添加商品到购物车
     * attributes.addFlashAttribute():将数据放在session中，可以在页面中取出，但是只能取一次
     * attributes.addAttribute():将数据放在url后面
     *
     * @return redirect url
     */
    @GetMapping(value = "/addCartItem")
    public String addCartItem(
            @RequestParam("skuId") Long skuId,
            @RequestParam("num") Integer num,
            RedirectAttributes attributes
    ) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, num);
        attributes.addAttribute("skuId", skuId);
        return "redirect:" + LEJING_CART_HOME + "/addToCartSuccessPage.html";
    }


    /**
     * 跳转到添加购物车成功页面
     *
     * @param skuId sku id
     * @return success
     */
    @GetMapping(value = "/addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
        //重定向到成功页面。再次查询购物车数据即可
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }


    /**
     * 商品是否选中
     *
     * @param skuId   skuId
     * @param checked 选中
     * @return redirect url
     */
    @GetMapping(value = "/checkItem")
    public String checkItem(
            @RequestParam(value = "skuId") Long skuId,
            @RequestParam(value = "checked") Integer checked
    ) {
        cartService.checkItem(skuId, checked);
        return "redirect:" + LEJING_CART_HTML;

    }

    /**
     * 改变商品数量
     *
     * @param skuId sku id
     * @param num   数量
     * @return redirect url
     */
    @GetMapping(value = "/countItem")
    public String countItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "num") Integer num) {
        cartService.changeItemCount(skuId, num);
        return "redirect:" + LEJING_CART_HTML;
    }

    /**
     * 删除商品信息
     *
     * @param skuId sku id
     * @return redirect url
     */
    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("skuId") Integer skuId) {
        cartService.deleteIdCartInfo(skuId);
        return "redirect:" + LEJING_CART_HTML;
    }
}
