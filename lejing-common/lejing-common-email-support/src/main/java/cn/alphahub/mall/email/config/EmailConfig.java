package cn.alphahub.mall.email.config;

import cn.alphahub.mall.email.annotation.Email;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.stream.Collectors;

import static cn.alphahub.mall.email.config.EmailConfig.EmailProperties;
import static cn.alphahub.mall.email.config.EmailConfig.EmailTemplateProperties;

/**
 * 邮件配置类
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-06
 */
@Configuration
@EnableAspectJAutoProxy
@DependsOn({"emailPropertiesMap", "javaMailSenderMap"})
@EnableConfigurationProperties({MailProperties.class, EmailProperties.class, EmailTemplateProperties.class})
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
}

