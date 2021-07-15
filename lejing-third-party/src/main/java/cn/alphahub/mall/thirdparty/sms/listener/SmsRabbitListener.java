package cn.alphahub.mall.thirdparty.sms.listener;

import cn.alphahub.mall.thirdparty.sms.util.AliyunSmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * <p>SMS消息监听器</p>
 * <p>当接收到消息后，我们使用MQ往队列里面发送短信发送短信</p>
 *
 * @author liuwenjing
 */
@Component
public class SmsRabbitListener {

    @Resource
    private AliyunSmsUtil aliyunSmsUtil;

    /**
     * 发送验证码给用户手机
     * <p>map入参用法:</p>
     * <pre>
     * Map<String, Object> map = new LinkedHashMap<>();
     * //一个验证码，字符类型
     * map.put("code", 785964);
     * //支持对多个手机号码发送短信,上限为1000个手机号码
     * map.put("phone", Arrays.asList("13012341234", "13812347894"));
     * //发送短信
     * sendSms(map);
     * </pre>
     *
     * @param map 入参map
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEJING.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(value = "LEJING.SMS.EXCHANGE", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"})
    )
    public void sendSms(Map<String, Object> map) {
        boolean flag = CollectionUtils.isEmpty(map)
                || Objects.isNull(map.get("phone"))
                || Objects.isNull(map.get("code"))
                || StringUtils.isBlank(map.get("phone").toString())
                || StringUtils.isBlank(map.get("code").toString());
        if (flag) {
            return;
        }
        aliyunSmsUtil.sendSms(map);
    }
}
