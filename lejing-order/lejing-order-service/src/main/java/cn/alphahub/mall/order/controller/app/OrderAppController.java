package cn.alphahub.mall.order.controller.app;

import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * 订单业务 Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/19
 */
@Controller
public class OrderAppController {
    @Resource
    private OrderService orderService;

    /**
     * 去结算确认页
     */
    @GetMapping(value = "/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrderData", confirmVo);
        //展示订单确认的数据
        return "confirm";
    }

}
