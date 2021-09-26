package cn.alphahub.mall.email.config;

import cn.alphahub.mall.email.enums.SmsSupplier;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static cn.alphahub.mall.email.config.SmsConfig.MultipleSmsTemplateProperties;
import static cn.alphahub.mall.email.config.SmsConfig.SmsProperties;
import static cn.alphahub.mall.email.config.SmsConfig.SmsTemplateProperties;

/**
 * 多模板短信配置
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Validated
@Configuration
@EnableConfigurationProperties({SmsProperties.class, SmsTemplateProperties.class, MultipleSmsTemplateProperties.class})
public class SmsConfig {

    /**
     * 短信配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms")
    public static class SmsProperties {
        /**
         * 短信access-key
         */
        @NotEmpty(message = "短信access-key不能为空")
        private String accessKey;
        /**
         * 短信secret-key
         */
        @NotEmpty(message = "短信secret-key不能为空")
        private String secretKey;
        /**
         * 区域
         */
        @NotEmpty(message = "区域id不能为空")
        private String regionId;
        /**
         * 短信签名
         */
        @NotEmpty(message = "短信签名不能为空")
        private String signName;
        /**
         * 短信模板
         */
        @NotEmpty(message = "短信模板不能为空")
        private String templateCode;
    }

    /**
     * 短信模板配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.multi-sms-template.template-properties")
    public static class SmsTemplateProperties {
        /**
         * 短信模板名称
         */
        @NotEmpty(message = "短信模板名称不能为空")
        private String templateName;
        /**
         * 短信供应商
         */
        @NotNull(message = "短信供应商不能为空")
        private SmsSupplier smsSupplier;
        /**
         * 短信配置元数据
         */
        @Valid
        private SmsProperties smsProperties;
    }

    /**
     * 多短信模板、多供应商配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.multi-sms-template")
    public static class MultipleSmsTemplateProperties {
        /**
         * 短信模板配置列表
         */
        private List<SmsTemplateProperties> templateProperties;
    }
}
