package cn.alphahub.mall.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 阿里云短信参数配置
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alibaba.cloud")
public class SmsProperties implements Serializable {
    private static final long serialVersionUID = 1L;

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
}
