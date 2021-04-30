package cn.alphahub.mall.order.controller.rpc;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.cart.vo.CartItemVo;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单业务 rpc Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/19
 */
@RestController
@RequestMapping("/order")
public class OrderRpcController {
    @Resource
    private OrderService orderService;

    /**
     * 获取当前用户的购物车商品项
     *
     * @return 用户当前的购物项
     */
    @GetMapping(value = "/items")
    public BaseResult<List<CartItemVo>> getCartItems() {
        System.err.println("获取当前用户的购物车商品项...");
        List<CartItemVo> userCartItems = orderService.getUserCartItems();
        System.err.println(JSONUtil.toJsonPrettyStr(userCartItems));
        return BaseResult.ok(userCartItems);
    }
}
