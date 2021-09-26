package cn.alphahub.mall.email.impl;

import cn.alphahub.mall.email.SmsSupport;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static cn.alphahub.mall.email.config.SmsConfig.SmsProperties;

/**
 * 阿里云短信实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
public class DefaultAliCloudSmsSupportImpl implements SmsSupport {
    /**
     * 短信API产品名称(短信产品名固定, 无需修改)
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 短信API产品域名 (接口地址固定, 无需修改)
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String VERSION = "2017-05-25";
    private static final String SEND_SMS = "SendSms";
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultAliCloudSmsSupportImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        CommonResponse response = new CommonResponse();
        if (StringUtils.isBlank(content)) {
            log.error("短信内容不能为空!");
            return null;
        }
        if (StringUtils.isAllBlank(phones)) {
            log.error("手机号不能为空!");
            return null;
        }
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DOMAIN);
        request.setSysVersion(VERSION);
        request.setSysAction(SEND_SMS);

        request.putQueryParameter("RegionId", ObjectUtils.defaultIfNull(smsProperties.getRegionId(), REGION_ID));
        // 支持对多个手机号码发送短信，手机号码之间以英文逗号","分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。验证码类型短信，建议使用单独发送的方式。
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
        request.putQueryParameter("PhoneNumbers", StringUtils.join(phones, ","));
        request.putQueryParameter("TemplateParam", "{\"content\":\"" + content + "\"}");

        try {
            response = this.getAcsClient().getCommonResponse(request);
        } catch (ClientException e) {
            log.error("发送短信异常：{}", e.getMessage(), e);
        }
        log.info("发送短信状态: {}", response.getData());

        return response;
    }

    /**
     * 获取IAcsClient实例
     *
     * @return IAcsClient instance
     */
    public IAcsClient getAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKey(), smsProperties.getSecretKey());
        DefaultProfile.addEndpoint(REGION_ID, PRODUCT, DOMAIN);
        return new DefaultAcsClient(profile);
    }
}
