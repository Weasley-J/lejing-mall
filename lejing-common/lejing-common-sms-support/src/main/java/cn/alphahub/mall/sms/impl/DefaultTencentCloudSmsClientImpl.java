package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.json.JSONUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * 腾讯云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class DefaultTencentCloudSmsClientImpl implements SmsClient {
    /**
     * 短信SdkAppId，在<a href='https://console.cloud.tencent.com/smsv2'>短信控制台</a>添加应用后生成的实际 SdkAppId，示例如1400006666
     * <p>更换为自己的短信SdkAppId</p>
     */
    private static final String SDK_APP_ID = "your_sdk_app_id";
    /**
     * 模板ID，必须填写已审核通过的模板ID。模板ID可登录<a href='https://console.cloud.tencent.com/smsv2/csms-template'>短信控制台</a>查看，若向境外手机号发送短信，仅支持使用国际/港澳台短信模板。
     * <p>更换为自己的短信模板ID</p>
     */
    private static final String TEMPLATE_ID = "your_template_id";
    /**
     * 短信配置元数据
     */
    private final SmsConfig.SmsProperties smsProperties;

    public DefaultTencentCloudSmsClientImpl(SmsConfig.SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        SendSmsResponse resp = new SendSmsResponse();
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(smsProperties.getAccessKey(), smsProperties.getSecretKey());
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            com.tencentcloudapi.sms.v20210111.SmsClient client = new com.tencentcloudapi.sms.v20210111.SmsClient(cred, ObjectUtils.defaultIfNull(smsProperties.getRegionId(), "ap-nanjing"), clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(phones);

            req.setSmsSdkAppId(ObjectUtils.defaultIfNull(smsProperties.getAppId(), SDK_APP_ID));
            req.setTemplateId(ObjectUtils.defaultIfNull(smsProperties.getTemplateCode(), TEMPLATE_ID));
            // 这里可以查看模板的签名[https://console.cloud.tencent.com/smsv2/csms-sign]
            req.setSignName(smsProperties.getSignName());

            //模板参数，若无模板参数，则设置为空。 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致。
            req.setTemplateParamSet(StringUtils.split(content, ","));

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            resp = client.SendSms(req);
            // 输出json格式的字符串回包
            log.info("response:{}", JSONUtil.toJsonStr(resp));
        } catch (TencentCloudSDKException e) {
            log.error("{}", e, e);
        }
        return resp;
    }
}
