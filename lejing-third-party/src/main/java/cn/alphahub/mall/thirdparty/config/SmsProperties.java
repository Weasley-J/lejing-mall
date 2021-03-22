package cn.alphahub.mall.thirdparty.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;

/**
 * 阿里云短信参数配置
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alibaba.cloud.sms")
public class SmsProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信API产品名称(短信产品名固定, 无需修改)
     */
    private static final String PRODUCT = "Dysmsapi";

    /**
     * 短信API产品域名 (接口地址固定, 无需修改)
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 短信API版本
     */
    private static final String VERSION = "2017-05-25";

    /**
     * 操作: SendSms
     */
    private static final String SEND_SMS = "SendSms";

    /**
     * 区域id: cn-hangzhou
     */
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 区域: 默认cn-hangzhou
     */
    private String regionId;

    /**
     * access key id
     */
    private String accessKeyId;

    /**
     * access secret
     */
    private String accessSecret;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板
     */
    private String templateCode;

    /**
     * 上行消息接收
     */
    private String upQueueName;

    /**
     * 状态报告接收
     */
    private String reportQueueName;

    /**
     * 获取IAcsClient实例
     *
     * @param smsProperties 阿里云短信参数配置
     * @return AcsClient instance
     */
    @Bean
    @Scope(value = DefaultListableBeanFactory.SCOPE_PROTOTYPE)
    public IAcsClient acsClient(SmsProperties smsProperties) {
        DefaultProfile profile = DefaultProfile.getProfile(
                // 地域ID
                smsProperties.getRegionId(),
                // RAM账号的 AccessKey ID
                smsProperties.getAccessKeyId(),
                // RAM账号的 AccessKey Secret
                smsProperties.getAccessSecret()
        );
        DefaultProfile.addEndpoint(REGION_ID, PRODUCT, DOMAIN);
        return new DefaultAcsClient(profile);
    }
}
