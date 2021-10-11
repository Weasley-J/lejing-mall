package cn.alphahub.mall.sms;

import cn.alphahub.mall.sms.aspect.SmsAspect;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import java.util.Arrays;

/**
 * SMS Config Tests
 * <p>
 * <a href='https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#features.developing-auto-configuration.testing'>spring boot starter的测试方式帮助文档</a>
 * </p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-10-11 11:42
 */
public class SmsConfigTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SmsConfig.class));

    @BeforeEach
    void setUp() {
        System.err.println("--------------------------------");

    }

    @AfterEach
    void tearDown() {
        System.err.println("--------------------------------");
    }

    @Test
    void testSmsConfig() {
        this.contextRunner.run(context -> {
            int beanDefinitionCount = context.getBeanDefinitionCount();
            System.err.println("bean数量 = " + beanDefinitionCount);
            String[] beanDefinitionNames = context.getBeanDefinitionNames();
            System.err.println("bean列表 = " + Arrays.asList(beanDefinitionNames));
        });
    }

    @Test
    void testSmsConfigThreadPoolProperties() {
        new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(SmsConfig.ThreadPoolProperties.class))
                .run(context -> {
                    int beanDefinitionCount = context.getBeanDefinitionCount();
                    System.err.println("bean数量 = " + beanDefinitionCount);

                    System.out.println("bean名称:");
                    String[] beanDefinitionNames = context.getBeanDefinitionNames();
                    for (String beanDefinitionName : beanDefinitionNames) {
                        System.err.println(beanDefinitionName);
                    }

                    SmsConfig.ThreadPoolProperties bean = context.getBean(SmsConfig.ThreadPoolProperties.class);
                    System.out.println("bean默认配置元数据:");
                    System.err.println(JSONUtil.toJsonPrettyStr(bean));
                })
        ;
    }

    @Test
    void testWebApplicationContextRunner() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withUserConfiguration(SmsConfig.class)
                .withUserConfiguration(SmsAspect.class)
                .withUserConfiguration(SmsTemplate.class);
    }
}
