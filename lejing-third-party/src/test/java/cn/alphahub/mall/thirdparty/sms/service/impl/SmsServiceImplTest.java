package cn.alphahub.mall.thirdparty.sms.service.impl;

import cn.alphahub.common.core.domain.SmsParam;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.common.util.NumberUtils;
import cn.alphahub.mall.thirdparty.sms.util.AliyunSmsUtil;
import com.aliyuncs.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class SmsServiceImplTest {
    /**
     * Redis验证码key前缀
     */
    private static final String KEY_PREFIX = "user:verify:";

    @Resource
    AliyunSmsUtil smsUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        System.out.println("---------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------");
    }

    @Test
    void sendSmsByEntity() {
        SmsParam smsParam = SmsParam.builder().code("123456").phone(new String[]{"19121716816"}).build();
        String serialize = JSONUtil.toJsonStr(smsParam);
        System.out.println(serialize);
        // smsUtil.sendSms(smsParam);
    }

    @Test
    void sendSmsByMap() {
        CommonResponse response = smsUtil.sendSms("123456", "19121716816");
        String string = response.toString();
        System.out.println(string);
    }

    @Test
    void sendCheckCode() {
        Boolean sendCheckCode = sendCheckCode("19121716816");
        System.out.println("发送结果:" + sendCheckCode);
    }

    /**
     * <b>发送验证码</b>
     *
     * @param phone 需要接收验证码的手机号
     */
    public Boolean sendCheckCode(String phone) {
        // 手机号为空直接返回
        if (StringUtils.isBlank(phone)) {
            return Boolean.FALSE;
        }
        // 如果redis里面存在则不需要重复发送, 防止恶意刷短信
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String redisExistsCode = ops.get(KEY_PREFIX.concat(phone));
        if (StringUtils.isNotBlank(redisExistsCode)) {
            log.warn("redis里面存在该手机号不需要重复发送!");
            return Boolean.TRUE;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("code", code);
        msgMap.put("phone", phone);
        //发消息到RabbitMQ
        try {
            // routingKey要和消息对列中的保持一致,
            // 提示: 我们已经在配置文件指定用于发送操的默认routing key了,这里就不指定routing key了
            // amqpTemplate.convertAndSend("sms.verify.code", msgMap);
            amqpTemplate.convertAndSend(msgMap);
            //保存验证码到redis缓存中,5min 内有效
            ops.set(KEY_PREFIX.concat(phone), code, 5, TimeUnit.MINUTES);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("发送短信失败! phone：{}， code：{}, 异常信息:{}\n", phone, code, e.getMessage(), e);
            return Boolean.FALSE;
        }
    }
}
