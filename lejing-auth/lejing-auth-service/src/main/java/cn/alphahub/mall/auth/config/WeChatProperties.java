package cn.alphahub.mall.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * <b>微信位置参数</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.open")
public class WeChatProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * app id
     */
    private String appId;

    /**
     * app secret
     */
    private String appSecret;

    /**
     * redirect url
     */
    private String redirectUrl;
}
