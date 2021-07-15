package cn.alphahub.mall.order.controller.web.pay;

import cn.alphahub.mall.order.config.alipay.AlipayService;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 阿里支付 - Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/27
 */
@Slf4j
@Controller
public class PayWebController {
    @Resource
    private AlipayService alipayService;
    @Resource
    private OrderService orderService;

    /**
     * 用户下单: 支付宝支付
     * <ul>
     *     <li>让支付页让浏览器展示</li>
     *     <li>支付成功以后，跳转到用户的订单列表页</li>
     * </ul>
     *
     * @param orderSn 订单号
     * @return 支付结果
     * @throws AlipayApiException alipay api exception
     */
    @ResponseBody
    @GetMapping(value = "/aliPayOrder", produces = MediaType.TEXT_HTML_VALUE)
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        var payVo = orderService.getOrderPaymentInfo(orderSn);
        log.info("当前订单的支付信息:{}", JSONUtil.toJsonStr(payVo));
        return alipayService.pay(payVo);
    }
}
