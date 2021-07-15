package cn.alphahub.mall.order.config.alipay;

import cn.alphahub.mall.order.dto.vo.PayVo;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付宝支付 - 业务类
 * <p>
 *     <ul>
 *         <li>电脑网站支付</li>
 *     </ul>
 * </p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/27
 */
@Data
@Slf4j
@Component
public class AlipayService {

    @Resource
    public AlipayProperties alipayProperties;

    /**
     * 获取支付结果
     *
     * @param pay 支付数据
     * @return 返回支付结果
     * @throws AlipayApiException 支付宝api异常
     */
    public String pay(PayVo pay) throws AlipayApiException {

        //1. 根据支付宝的配置生成一个支付客户端
        var alipayClient = new DefaultAlipayClient(
                alipayProperties.getGatewayUrl(), alipayProperties.getAppId(), alipayProperties.getMerchantPrivateKey(), "json",
                alipayProperties.getCharset(), alipayProperties.getAlipayPublicKey(), alipayProperties.getSignType()
        );
        //2. 创建一个支付请求, 置请求参数
        var payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(alipayProperties.getReturnUrl());
        payRequest.setNotifyUrl(alipayProperties.getNotifyUrl());

        //3. 封装入参map
        Map<String, Object> params = new LinkedHashMap<>(20);
        params.put("out_trade_no", pay.getOutTradeNo());
        params.put("total_amount", pay.getTotalAmount());
        params.put("subject", pay.getSubject());
        params.put("body", pay.getBody());
        params.put("timeout_express", alipayProperties.getTimeout());
        params.put("product_code", "FAST_INSTANT_TRADE_PAY");

        payRequest.setBizContent(JSONUtil.toJsonStr(params));

        AlipayTradePagePayResponse payResponse = alipayClient.pageExecute(payRequest);
        String payResult = payResponse.getBody();
        //4. 收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        log.info("支付宝支付的响应:\n{}\n", payResult);
        return payResult;
    }


    /**
     * 支付宝支付配置参数
     */
    @Data
    @Component
    @ConfigurationProperties(prefix = "lejing.thirdpay.alipay")
    public static class AlipayProperties implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
         */
        private String appId;
        /**
         * 商户私钥，您的PKCS8格式RSA2私钥
         */
        private String merchantPrivateKey;
        /**
         * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
         */
        private String alipayPublicKey;
        /**
         * 支付宝网关； https://openapi.alipaydev.com/gateway.do
         */
        private String gatewayUrl;
        /**
         * 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息,服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
         * <ul>
         *     <li>POST请求</li>
         * </ul>
         */
        private String notifyUrl;
        /**
         * 同步通知，支付成功，一般跳转到成功页，页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
         */
        private String returnUrl;
        /**
         * 签名方式, 如： RSA2
         */
        private String signType = "RSA2";
        /**
         * 字符编码格式, 如：UTF-8
         */
        private String charset = "UTF-8";
        /**
         * 订单超时时间，如：1m
         * <ul>
         *     <li>用于支付宝自动收单</li>
         * </ul>
         */
        private String timeout = "1m";
    }
}
