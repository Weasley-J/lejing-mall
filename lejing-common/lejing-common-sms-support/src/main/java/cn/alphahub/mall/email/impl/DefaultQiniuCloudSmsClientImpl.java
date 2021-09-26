package cn.alphahub.mall.email.impl;

import cn.alphahub.mall.email.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static cn.alphahub.mall.email.config.SmsConfig.SmsProperties;

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
    private final SmsProperties smsProperties;

    public DefaultQiniuCloudSmsClientImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        return null;
    }
}
