package cn.alphahub.mall.thirdparty.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 阿里云OSS属性
 *
 * @author liuwenjing
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alibaba.cloud")
public class OssProperties {

    /**
     * access-key
     */
    private String accessKey;

    /**
     * secret-key
     */
    private String secretKey;

    /**
     * bucket-name
     */
    private String bucketName;

    /**
     * host-prefix
     */
    private String hostPrefix;

    /**
     * oss-endpoint
     */
    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;

}
