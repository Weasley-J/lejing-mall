package cn.alphahub.mall.thirdparty.sms.service.impl;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.enums.CheckCodeOrigin;
import cn.alphahub.mall.thirdparty.config.AliyunConfig;
import cn.hutool.core.date.DateUtil;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 短信业务实现
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Slf4j
@Service
public final class SmsServiceImpl extends AbstractSmsService {

    @Resource
    private AliyunConfig.SmsProperties smsProperties;
    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request, String accessKeyId, String accessKeySecret) throws ClientException {
        return getHangZhouRegionClientProfile(accessKeyId, accessKeySecret).getAcsResponse(request);
    }

    @Override
    public QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request) throws ClientException {
        return querySendDetails(request, smsProperties.getAccessKeyId(), smsProperties.getAccessSecret());
    }

    /**
     * 查询短信发送详情
     *
     * @param telephone 手机号
     * @param sendDate  发送日期
     * @return 发送结果
     */
    @Override
    public QuerySendDetailsResponse querySendDetails(String telephone, @DateTimeFormat(pattern = "yyyy-MM-dd") Date sendDate) {
        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd

        request.setSendDate(DateUtil.format(sendDate, "yyyyMMdd"));
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            return querySendDetails(request);
        } catch (ClientException e) {
            log.error("发送短信异常：{}\n", e.getMessage(), e);
            return null;
        }
    }

    /**
     * <b>发送验证码</b>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 是否成功
     */
    @Override
    public Boolean sendCheckCode(String phone, Integer origin) {
        // 手机号为空直接返回
        if (StringUtils.isBlank(phone)) {
            return Boolean.FALSE;
        }
        // 如果redis里面存在则不需要重复发送, 防止恶意刷短信
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String redisExistsCode = ops.get(AuthConstant.REDIS_KEY_PREFIX + phone);
        if (StringUtils.isNotBlank(redisExistsCode)) {
            log.warn("redis里面存在该手机号不需要重复发送!");
            return Boolean.TRUE;
        }
        //获取验证码长度:移动端4位,浏览器6位, origin:1-使用移动端请求验证码,2-使用浏览器请求验证码,0-未知来源, origin为空验证码长度6位
        int checkCodeLength;
        if (Objects.nonNull(origin)) {
            if (Objects.equals(CheckCodeOrigin.MOBILE.getValue(), origin)) checkCodeLength = 4;
            else checkCodeLength = 6;
        } else {
            checkCodeLength = 6;
        }
        //生成验证码
        String code = RandomStringUtils.randomNumeric(checkCodeLength);
        Map<String, Object> msgMap = new HashMap<>(2);
        msgMap.put("code", code);
        msgMap.put("phone", phone);
        //发消息到RabbitMQ
        try {
            // routingKey要和消息对列中的保持一致
            // 提示: 我们已经在配置文件指定用于发送操的默认routing key了,这里就不指定routing key了, 如果指定routingKey: mqpTemplate.convertAndSend("sms.verify.code", msgMap)
            amqpTemplate.convertAndSend(msgMap);
            //保存验证码到redis缓存中,5min 内有效
            ops.set(AuthConstant.REDIS_KEY_PREFIX + phone, code, 15, TimeUnit.MINUTES);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("发送短信失败! phone：{}， code：{}, 异常信息:{}\n", phone, code, e.getMessage(), e);
            return Boolean.FALSE;
        }
    }
}
