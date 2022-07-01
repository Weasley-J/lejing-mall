package cn.alphahub.mall.common.config;


import cn.alphahub.mall.common.constant.FrameworkConstant;
import cn.alphahub.mall.common.interceptor.DefaultRpcTraceInterceptor;
import cn.alphahub.mall.common.interceptor.DefaultTraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 基础应用配置
 *
 * @author weasley
 * @version 1.0
 * @date 2022/6/30
 */
@Slf4j
@Configuration
public class BaseApplicationConfig implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .maxAge(3600L);
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultRpcTraceInterceptor()).addPathPatterns(FrameworkConstant.URL_API_PREFIX_RPC + "/**");
        registry.addInterceptor(new DefaultTraceInterceptor()).addPathPatterns("/**");
    }
}
