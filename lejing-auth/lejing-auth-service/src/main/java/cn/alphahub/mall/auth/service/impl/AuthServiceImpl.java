package cn.alphahub.mall.auth.service.impl;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.enumeration.CheckCodeOrigin;
import cn.alphahub.common.enumeration.CheckCodeStatus;
import cn.alphahub.common.util.NumberUtils;
import cn.alphahub.mall.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <b>授权服务-业务实现</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * <b>发送验证码</b>
     * <p>使用RabbitMQ发送短信，使用Redis做短信防刷</p>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 验证码发送结果枚举提示
     */
    @Override
    public CheckCodeStatus sendCheckCode(String phone, Integer origin) {
        String splitString = ",";
        // 手机号为空直接返回
        if (StringUtils.isBlank(phone)) {
            return CheckCodeStatus.EMPTY_PHONE;
        }
        // 如果redis里面存在则不需要重复发送, 防止恶意刷短信
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String cacheKey = AuthConstant.REDIS_KEY_PREFIX + phone;
        String redisExistsCode = ops.get(cacheKey);
        // 短信防刷逻辑
        if (StringUtils.isNotBlank(redisExistsCode)) {
            String[] split = redisExistsCode.split(splitString);
            long parsedRetrySeconds = Long.parseLong(split[1]), retrySeconds = 120L, retryMillis = retrySeconds * 1000L;
            // 120秒内不能重发
            CheckCodeStatus send = CheckCodeStatus.SEND;
            if (System.currentTimeMillis() - parsedRetrySeconds < retryMillis) {
                log.warn("redis里面存在该手机号对应的验证码，" + retrySeconds + "秒内不能要重复发送!");
                send.setName("请求短信验证码频次太高，" + retrySeconds + "秒内不能重发！");
                return send;
            }
        }
        //获取验证码长度:移动端4位,浏览器6位, origin:1-使用移动端请求验证码,2-使用浏览器请求验证码,0-未知来源, origin为空验证码长度6位
        int checkCodeLength = Objects.nonNull(origin) ? Objects.equals(CheckCodeOrigin.MOBILE.getValue(), origin) ? 4 : 6 : 6;
        //生成验证码
        String code = NumberUtils.generateCode(checkCodeLength);
        Map<String, Object> msgMap = new HashMap<>(2);
        msgMap.put("code", code);
        msgMap.put("phone", phone);
        //发消息到RabbitMQ
        try {
            // routingKey要和消息对列中的保持一致
            // 提示: 我们已经在配置文件指定用于发送操的默认routing key了,这里就不指定routing key了, 如果指定routingKey: mqpTemplate.convertAndSend("sms.verify.code", msgMap)
            amqpTemplate.convertAndSend(msgMap);
            //給验证码拼上当前系统的时间戳, 保存验证码到redis缓存中, 15min 内有效;
            code = code + splitString + System.currentTimeMillis();
            ops.set(cacheKey, code, 15, TimeUnit.MINUTES);
            return CheckCodeStatus.SUCCESS;
        } catch (Exception e) {
            log.error("发送短信失败! phone：{}， code：{}, 异常信息:{}\n", phone, code, e.getMessage(), e);
            return CheckCodeStatus.ERROR;
        }
    }
}
