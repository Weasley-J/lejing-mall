package cn.alphahub.mall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <b>视图跳转配置</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/18
 */
@Configuration
public class ViewControllerConfig implements WebMvcConfigurer {

    /**
     * 添加视图控制器
     *
     * @param registry 自动映射的视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 登录页html
        // registry.addViewController("/login.html").setViewName("login");
        // 注册页html
        registry.addViewController("/reg.html").setViewName("reg");
    }
}
