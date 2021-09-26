package cn.alphahub.mall.email.impl;

import cn.alphahub.mall.email.SmsSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
public class DefaultJingdongCloudSmsSupportImpl implements SmsSupport {

    @Override
    public Object send(String content, String... phones) {
        return null;
    }
}
