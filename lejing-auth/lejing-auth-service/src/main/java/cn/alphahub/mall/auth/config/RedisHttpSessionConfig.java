package cn.alphahub.mall.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * <b>Spring session配置类</b>
 * <P>使用redis存储session信息</P>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/28
 */
@Configuration
public class RedisHttpSessionConfig {
    private static final String DOMAIN_NAME = "lejing.com";
    private static final String COOKIE_NAME = "LEJING_SESSION";

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 放大作用域
        cookieSerializer.setDomainName(DOMAIN_NAME);
        cookieSerializer.setCookieName(COOKIE_NAME);
        return cookieSerializer;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
