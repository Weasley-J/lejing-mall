package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义短信发送实现类实例
 * <p>这个示例是自定义实现短信服务的示例</p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-29 16:45
 */
@Slf4j
@Component
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class MyCustomSmsClientDemoImpl implements SmsClient {

    /**
     * 短信配置元数据
     * <p>如果你有默认短信配置，你也可以在当前模块的{@code src/main/resources/application-sms.yml}里面配置默认元数数据</p>
     */
    @Resource
    private SmsConfig.SmsProperties smsProperties;

    /**
     * 发送短信
     *
     * @param content 短信内容、模板参数。多个以","隔开，若无模板参数，则为短信内容。模板参数的个数需要与【短信模板】对应模板的变量个数保持一致。
     * @param phones  接收短信的手机号，可变参，可以是多个
     * @return 短信供应商的发送短信后的返回结果
     */
    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        Map<String, Object> respMap = new LinkedHashMap<>(3);
        respMap.put("content", content);
        respMap.put("phones", phones);
        String sendSmsResponse = "自定义短信实现:"+JSONUtil.toJsonStr(respMap);
        return sendSmsResponse;
    }
}
