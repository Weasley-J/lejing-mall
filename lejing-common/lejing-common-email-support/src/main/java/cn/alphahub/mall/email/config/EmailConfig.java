package cn.alphahub.mall.email.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 邮件配置类
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-06
 */
@Configuration
@EnableConfigurationProperties({MailProperties.class, EmailConfig.EmailProperties.class, EmailConfig.EmailTemplate.class})
public class EmailConfig {

    private EmailConfig() {
    }

    // TODO 使用注解@Mail指定以那个模板发送邮件

    /**
     * 邮件模板配置列表元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.mail.email-templates")
    public static class EmailTemplate {
        /**
         * 多邮件模板配置列表
         */
        private List<EmailProperties> emailTemplates;
    }

    /**
     * 单个邮件模板配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.mail")
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

