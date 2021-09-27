package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class DefaultJingdongCloudSmsClientImpl implements SmsClient {

    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultJingdongCloudSmsClientImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        return null;
    }
}
