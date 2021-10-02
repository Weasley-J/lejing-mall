package cn.alphahub.mall.email.config;

import cn.alphahub.mall.email.annotation.Email;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.alphahub.mall.email.config.EmailConfig.EmailProperties;
import static cn.alphahub.mall.email.config.EmailConfig.EmailTemplateProperties;
import static cn.alphahub.mall.email.config.EmailConfig.ThreadPoolProperties;

/**
 * 邮件配置类
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-06
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@DependsOn({"emailPropertiesMap", "javaMailSenderMap"})
@EnableConfigurationProperties({
        MailProperties.class, EmailProperties.class,
        EmailTemplateProperties.class, ThreadPoolProperties.class
})
public class EmailConfig {

    /**
     * 填充邮件模板配置列表元数据Map
     *
     * @param mailProperties          spring原生电子邮件支持的配置属性
     * @param emailTemplateProperties 多邮件模板配置列表元数据属性
     * @return 填充邮件模板配置列表元数据Map
     */
    @RefreshScope
    @Bean(name = {"emailPropertiesMap"})
    public Map<String, MailProperties> emailPropertiesMap(MailProperties mailProperties, EmailTemplateProperties emailTemplateProperties) {
        Map<String, MailProperties> mailPropertiesMap = new ConcurrentHashMap<>(100);
        mailPropertiesMap.put(Email.DEFAULT_TEMPLATE, mailProperties);
        List<EmailProperties> templates = emailTemplateProperties.getEmailTemplates();
        if (CollUtil.isEmpty(templates)) {
            return mailPropertiesMap;
        }
        Map<String, MailProperties> propertiesMap = templates.stream().collect(Collectors.toMap(EmailProperties::getTemplateName, EmailProperties::getMailProperties));
        mailPropertiesMap.putAll(propertiesMap);
        return mailPropertiesMap;
    }

    /**
     * 邮件发送对象Map
     * <p>spring初始化时把所有邮件模板的发送对象实例创建好注入IOC，对象只创建一次</p>
     *
     * @param emailPropertiesMap 填充邮件模板配置列表元数据Map
     * @return javaMailSenderMap邮件发送对象实例
     */
    @RefreshScope
    @Bean(name = {"javaMailSenderMap"})
    public Map<String, JavaMailSender> javaMailSenderMap(@Qualifier("emailPropertiesMap") Map<String, MailProperties> emailPropertiesMap) {
        Map<String, JavaMailSender> javaMailSenderMap = new ConcurrentHashMap<>(100);
        emailPropertiesMap.forEach((templateName, properties) -> {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(properties.getHost());
            if (properties.getPort() != null) {
                sender.setPort(properties.getPort());
            }
            sender.setUsername(properties.getUsername());
            sender.setPassword(properties.getPassword());
            sender.setProtocol(properties.getProtocol());
            if (properties.getDefaultEncoding() != null) {
                sender.setDefaultEncoding(properties.getDefaultEncoding().name());
            }
            if (!properties.getProperties().isEmpty()) {
                Properties asProperties = new Properties();
                asProperties.putAll(properties.getProperties());
                sender.setJavaMailProperties(asProperties);
            }
            javaMailSenderMap.put(templateName, sender);
            System.out.println("Loading instance of java mail sender, Template name '" + templateName + "', SMTP server '" + properties.getHost() + "'.");
        });
        return javaMailSenderMap;
    }

    /**
     * 线程池
     *
     * @return thread pool executor
     */
    @Bean
    @ConditionalOnMissingBean(name = {"threadPoolExecutor"})
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
        return new ThreadPoolExecutor(
                threadPoolProperties.getCorePoolSize(),
                threadPoolProperties.getMaximumPoolSize(),
                threadPoolProperties.getKeepAliveTime(),
                threadPoolProperties.getTimeUnit(),
                new LinkedBlockingQueue<>(threadPoolProperties.getCapacity()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 多邮件模板配置列表元数据属性
     */
    @Data
    @ConfigurationProperties(prefix = "spring.mail")
    public static class EmailTemplateProperties {
        /**
         * 多邮件模板配置列表
         */
        private List<EmailProperties> emailTemplates;
    }

    /**
     * 单个邮件模板配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.mail.email-templates")
    public static class EmailProperties {
        /**
         * 邮件模板名称
         */
        private String templateName;
        /**
         * 邮件模板配置信息
         */
        @NestedConfigurationProperty
        private MailProperties mailProperties;
    }

    /**
     * 线程池配置参数
     */
    @Data
    @ConfigurationProperties(prefix = "spring.mail.thread")
    public static class ThreadPoolProperties {
        /**
         * 核心线程池数量，默认：50
         */
        private Integer corePoolSize = 50;
        /**
         * 最大线程数，默认：200
         */
        private Integer maximumPoolSize = 200;
        /**
         * 存活时间，默认：10
         */
        private Long keepAliveTime = 10L;
        /**
         * 存活时间单位，默认：{@code TimeUnit.SECONDS}
         *
         * @see TimeUnit
         */
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        /**
         * 最大任务数量，默认：2000
         */
        private Integer capacity = 2000;
    }
}

