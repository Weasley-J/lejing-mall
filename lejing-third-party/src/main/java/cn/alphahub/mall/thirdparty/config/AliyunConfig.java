package cn.alphahub.mall.thirdparty.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static cn.alphahub.mall.thirdparty.config.AliyunConfig.OssProperties;
import static cn.alphahub.mall.thirdparty.config.AliyunConfig.SmsProperties;

/**
 * 阿里云配置类: OSS, SMS
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-29
 */
@Configuration
@EnableConfigurationProperties({OssProperties.class, SmsProperties.class})
public class AliyunConfig {
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
     * 获取IAcsClient实例
     *
     * @param smsProperties 阿里云短信参数配置
     * @return AcsClient instance
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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

    /**
     * 阿里云OSS属性
     *
     * @author liuwenjing
     */
    @Data
    @ConfigurationProperties(prefix = "alibaba.cloud")
    public static class OssProperties {
        /**
         * access-key
         */
        private String accessKey;
        /**
         * secret-key
         */
        private String secretKey;
        /**
         * OSS存储空间名称
         * <p>存储空间是OSS的全局命名空间，相当于数据的容器，可以存储若干文件</p>
         */
        private String bucketName;
        /**
         * Bucket域名前缀
         * <p>
         * 访问域名带上文件路径，即可通过互联网访问到 Bucket 内的任意文件
         * 如果 ACL 是私有读，则还需要带上签名，
         * URL 拼写规则：{@code https://${alibaba.cloud.bucket-name}.${alibaba.cloud.oss.endpoint}}, 如：<br/>
         * https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/object
         * </p>
         */
        private String hostPrefix;
        /**
         * 外网可访问的url前缀：https://${alibaba.cloud.bucket-name}.${alibaba.cloud.oss.endpoint}/
         * <p>直接拼后缀外网可访问</p>
         */
        private String urlPrefix;
        /**
         * 存储空间所在的地域对应的访问域名
         * <p>
         * <b>存储空间所在的地域对应的访问域名</b>
         *     <ul>
         *         <li>OSS的访问地址为固定格式：BucketName.Endpoint</li>
         *         <a href="https://help.aliyun.com/knowledge_detail/39584.html?spm=a2c4g.11186623.6.621.71e2414f7WKEBH">ECS实例通过OSS内网地址访问OSS资源</a>
         *         <a href="https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.22.606a7f5egqBfwS#concept-zt4-cvy-5db">各地域Endpoint详情请参见</a>
         *        <li>
         *            通过OSS内网地址访问OSS资源有以下两种方式：
         * <p>与OSS同地域ECS实例可以直接通过内网访问有权限的OSS资源</p>
         * <p>与OSS不同地域的ECS实例或公网用户可通过配置ECS反向代理，间接实现通过OSS内网地址访问OSS资源</p>
         *         </li>
         *     </ul>
         * </p>
         */
        @Value("${alibaba.cloud.oss.endpoint}")
        private String endpoint;
    }

    /**
     * 阿里云短信参数配置
     *
     * @author Weasley J
     * @date 2021年3月19日
     */
    @Data
    @ConfigurationProperties(prefix = "alibaba.cloud.sms")
    public static class SmsProperties {
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
    }
}
