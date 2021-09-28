package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;

/**
 * 京东云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
@Validated
public class DefaultJingdongCloudSmsClientImpl implements SmsClient {

    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultJingdongCloudSmsClientImpl(SmsProperties smsProperties) {
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
