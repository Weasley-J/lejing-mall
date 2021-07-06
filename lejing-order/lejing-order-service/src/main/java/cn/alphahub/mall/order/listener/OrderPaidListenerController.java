package cn.alphahub.mall.order.listener;

import cn.alphahub.mall.order.config.alipay.AlipayService;
import cn.alphahub.mall.order.dto.vo.PayAsyncVo;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 支付宝订单支付成功回调Controller
 *
 * @author liuwenjjing
 * @version 1.0
 * @date 2021/06/29
 */
@Slf4j
@RestController
public class OrderPaidListenerController {

    @Resource
    public AlipayService.AlipayProperties alipayProperties;
    @Resource
    private OrderService orderService;

    /**
     * 接受支付宝支付成功异步回调
     *
     * @param asyncVo 付款异步回调数据
     * @throws AlipayApiException
     */
    @PostMapping(value = "/payed/notify")
    public String handlePaidEvent(PayAsyncVo asyncVo, HttpServletRequest request) throws AlipayApiException {
        log.info("收到支付宝异步通知:{}", JSONUtil.toJsonPrettyStr(asyncVo));
        // 获取支付宝POST过来反馈信息, 只要收到支付宝的异步通知，返回 success 支付宝便不再通知
        Map<String, String> params = new LinkedHashMap<>(35);
        Set<Map.Entry<String, String[]>> entries = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            Optional<String> first = Arrays.stream(entry.getValue()).findFirst();
            if (first.isPresent()) {
                String value = first.get();
                params.put(key, value);
            }
        }
        // 调用SDK验证签名
        boolean checked = AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());
        log.info("签名验证验证结果:{}", checked);
        return checked ? orderService.handlePaidResult(asyncVo) : "error";
    }
}
