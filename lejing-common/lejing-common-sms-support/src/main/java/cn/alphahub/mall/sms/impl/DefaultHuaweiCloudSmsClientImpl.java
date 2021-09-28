package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 华为云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
@Validated
public class DefaultHuaweiCloudSmsClientImpl implements SmsClient {
    /**
     * 短信配置元数据
     */
    private final SmsConfig.SmsProperties smsProperties;

    public DefaultHuaweiCloudSmsClientImpl(SmsConfig.SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(@NotBlank String content, @NotEmpty String... phones) {
        if (parameterIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("content or phones is empty.");
        }
        return null;
    }
}
