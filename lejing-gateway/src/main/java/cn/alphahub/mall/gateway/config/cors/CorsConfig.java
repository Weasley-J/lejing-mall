package cn.alphahub.mall.gateway.config.cors;

import lombok.Data;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * 跨域配置类，解决ajax请求跨域问题
 *
 * @author liuwenjing
 * @date 2021年2月12日
 */
@Data
//@Configuration
//@ConfigurationProperties(prefix = "cors.config")
public class CorsConfig {

    /**
     * 是否允许所有域名跨域：true|false
     */
    private Boolean isAllowsAllDomain;

    /**
     * 允许跨域的域名
     */
    private List<String> allowedOrigins;

    //@Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许的域,不要写*，否则cookie就无法使用了
        config.setAllowedOrigins(isAllowsAllDomain ? Collections.singletonList("*") : allowedOrigins);
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("*"));
        // 是否允许携带cookie进行跨域
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        // 对接口进行配置，“/*”代表所有，“/**”代表适配的所有接口
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
