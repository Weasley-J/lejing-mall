package cn.alphahub.mall.order.controller.app;

import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 订单业务 Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/19
 */
@Slf4j
@Controller
public class OrderAppController {
    @Resource
    private OrderService orderService;

    /**
     * 去订单结算确认页
     *
     * @return 订单确认页
     */
    @GetMapping(value = "/toTrade")
    public String toTrade(Model model, HttpServletRequest request) {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        log.info("订单确认页数据:{}", JSONUtil.toJsonPrettyStr(confirmVo));
        model.addAttribute("confirmOrderData", confirmVo);
        return "confirm";
    }
}
