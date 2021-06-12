package cn.alphahub.mall.order.controller.app;

import cn.alphahub.common.exception.NoStockException;
import cn.alphahub.mall.order.constant.OrderConstant;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.dto.vo.OrderSubmitVo;
import cn.alphahub.mall.order.dto.vo.SubmitOrderResponseVo;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
        log.info("订单确认页数据:{}", JSONUtil.toJsonStr(confirmVo));
        model.addAttribute("confirmOrderData", confirmVo);
        return "confirm";
    }

    /**
     * 提交订单结算
     *
     * @param submitVo 订单提交数据
     * @return 提交订单响应数据
     */
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model, RedirectAttributes attributes) {
        log.info("提交订单结算:{}", JSONUtil.toJsonStr(submitVo));
        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(submitVo);
            //下单成功来到支付选择页，下单失败回到订单确认页重新确定订单信息
            if (Objects.equals(0, responseVo.getCode())) {
                //成功
                model.addAttribute("submitOrderResp", responseVo);
                return "pay";
            } else {
                String msg = "下单失败：";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "令牌订单信息过期，请刷新再次提交";
                        break;
                    case 2:
                        msg += "订单商品价格发生变化，请确认后再次提交";
                        break;
                    case 3:
                        msg += "库存锁定失败，" + responseVo.getMsg();
                        break;
                    default:
                        break;
                }
                attributes.addFlashAttribute("msg", msg);
                return "redirect:" + OrderConstant.TO_TRADE_URL;
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                attributes.addFlashAttribute("msg", e.getMessage());
            }
            return "redirect:" + OrderConstant.TO_TRADE_URL;
        }
    }
}
