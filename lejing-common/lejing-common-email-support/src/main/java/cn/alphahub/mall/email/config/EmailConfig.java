package cn.alphahub.mall.email.config;

import cn.alphahub.mall.email.annotation.Email;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
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
@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({MailProperties.class, EmailProperties.class, EmailTemplateProperties.class})
public class EmailConfig {
    /**
     * 填充邮件模板配置列表元数据Map
     *
     * @param mailProperties          spring原生电子邮件支持的配置属性
     * @param emailTemplateProperties 多邮件模板配置列表元数据属性
     * @return 填充邮件模板配置列表元数据Map
     */
    @Bean(name = {"emailPropertiesMap"})
    public Map<String, MailProperties> emailPropertiesMap(MailProperties mailProperties,
                                                          EmailTemplateProperties emailTemplateProperties
    ) {
        Map<String, MailProperties> map = Maps.newLinkedHashMap();
        map.put(Email.DEFAULT_TEMPLATE, mailProperties);
        List<EmailProperties> templates = emailTemplateProperties.getEmailTemplates();
        if (CollUtil.isEmpty(templates)) {
            return map;
        }
        Map<String, MailProperties> propertiesMap = templates.stream().collect(Collectors.toMap(EmailProperties::getTemplateName, EmailProperties::getMailProperties));
        map.putAll(propertiesMap);
        return propertiesMap;
    }

    /**
     * 装载Bean
     *
     * @param templateName       电子邮件支持的配置属性
     * @param emailPropertiesMap 邮件模板配置列表元数据Map
     * @return JavaMailSender
     */
    public JavaMailSender mailSender(Map<String, MailProperties> emailPropertiesMap, String templateName) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        MailProperties properties = emailPropertiesMap.get(templateName);
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
        return sender;
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
        private MailProperties mailProperties;
    }
}

