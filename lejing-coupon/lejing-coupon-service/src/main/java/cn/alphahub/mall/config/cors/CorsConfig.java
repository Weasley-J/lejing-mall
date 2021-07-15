package cn.alphahub.mall.config.cors;

import lombok.Data;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 配置类，解决ajax请求跨域问题
 *
 * @author liuwenjing
 * @date 2021年2月3日
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
    public CorsFilter corsFilter() {
        //CORS allowed Origins according to version of Springboot
        String springBootVersion = SpringBootVersion.getVersion();
        String[] versionArray = springBootVersion.split("\\.");

        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        if (Integer.parseInt(versionArray[1]) >= 4) {
            config.setAllowedOriginPatterns(isAllowsAllDomain ? Collections.singletonList("*") : allowedOrigins);
        } else {
            config.setAllowedOrigins(isAllowsAllDomain ? Collections.singletonList("*") : allowedOrigins);
        }
        //2) 是否发送Cookie信息
        config.setAllowCredentials(Boolean.TRUE);
        //3) 允许的请求方式
        config.setAllowedMethods(Arrays.asList("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        //4）允许的头信息
        config.setAllowedHeaders(Collections.singletonList("*"));
        //5）暴露的头信息
        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
