package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 七牛云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
public class DefaultQiniuCloudSmsClientImpl implements SmsClient {

    /**
     * 短信配置元数据
     */
    private final SmsConfig.SmsProperties smsProperties;

    public DefaultQiniuCloudSmsClientImpl(SmsConfig.SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        return null;
    }
}
