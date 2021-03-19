package cn.alphahub.mall.sms.listener;

import cn.alphahub.common.core.domain.SmsParam;
import cn.alphahub.mall.sms.util.AliyunSmsUtil;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 消息监听器
 * 当接收到消息后，我们使用MQ往队列里面发送短信发送短信
 *
 * @author liuwenjing
 */
@Component
public class SmsRabbitListener {

    @Resource
    private AliyunSmsUtil aliyunSmsUtil;
    @Resource
    private ObjectMapper objectMapper;

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
     * @throws ClientException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEJING.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(value = "LEJING.SMS.EXCHANGE", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"})
    )
    public void sendSms(Map<String, Object> map) throws ClientException {
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

    /**
     * 发送验证码给用户手机
     *
     * @param smsParam 短信参数实体
     * @throws JsonProcessingException
     * @throws ClientException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEJING.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(value = "LEJING.SMS.EXCHANGE", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"})
    )
    public void sendSms(String smsParam) throws JsonProcessingException, ClientException {
        if (StringUtils.isBlank(smsParam)) {
            return;
        }
        SmsParam param = objectMapper.readValue(smsParam, SmsParam.class);
        aliyunSmsUtil.sendSms(param);
    }
}
