package cn.alphahub.mall.sms.config;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.enums.SmsSupplier;
import cn.alphahub.mall.sms.impl.DefaultAliCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultHuaweiCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultJingdongCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultQiniuCloudSmsClientImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.alphahub.mall.sms.config.SmsConfig.MultipleSmsTemplateProperties;
import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;
import static cn.alphahub.mall.sms.config.SmsConfig.SmsTemplateProperties;

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
@EnableAspectJAutoProxy
@EnableConfigurationProperties({SmsProperties.class, SmsTemplateProperties.class, MultipleSmsTemplateProperties.class})
public class SmsConfig {

    /**
     * 装饰的短信模板名称
     *
     * @param smsSupplier  短信供应商
     * @param templateName 配置文件里指定的短信模板名称
     * @return 短信模板名称
     */
    public String decorateTemplateName(@NotNull SmsSupplier smsSupplier, @NotEmpty String templateName) {
        return smsSupplier.getCode() + ":" + templateName;
    }

    /**
     * 短信模板配置集合
     *
     * @param smsProperties              短信配置元数据
     * @param multiSmsTemplateProperties 多短信模板、多供应商配置元数据
     * @return 短信模板配置集合
     * @apiNote 初始化短信模板配置集合为50个（包含默认短信模板）
     */
    @Bean({"smsPropertiesMap"})
    public Map<String, SmsTemplateProperties> smsPropertiesMap(SmsProperties smsProperties, MultipleSmsTemplateProperties multiSmsTemplateProperties) {
        Map<String, SmsTemplateProperties> smsSupportMap = new LinkedHashMap<>(50);
        if (Objects.nonNull(smsProperties)) {
            SmsTemplateProperties properties = new SmsTemplateProperties();
            properties.setTemplateName(SMS.DEFAULT_TEMPLATE);
            properties.setSmsSupplier(SmsSupplier.ALI);
            properties.setSmsProperties(smsProperties);
            String decorateTemplateName = decorateTemplateName(properties.getSmsSupplier(), properties.getTemplateName());
            smsSupportMap.put(decorateTemplateName, properties);
            log.info("Loaded default sms template '{}'", decorateTemplateName);
        }
        for (SmsTemplateProperties templateProperty : multiSmsTemplateProperties.getTemplateProperties()) {
            String decorateTemplateName = decorateTemplateName(templateProperty.getSmsSupplier(), templateProperty.getTemplateName());
            smsSupportMap.putIfAbsent(decorateTemplateName, templateProperty);
            log.info("Loaded multiple sms template '{}'", decorateTemplateName);
        }
        return smsSupportMap;
    }

    /**
     * 多模板、多供应商短信发送实例对象集合
     *
     * @param smsPropertiesMap 短信模板配置集合
     * @return 多模板、多供应商短信发送实例对象集合
     */
    @Bean({"smsClientMap"})
    public Map<String, SmsClient> smsClientMap(@Qualifier("smsPropertiesMap") Map<String, SmsTemplateProperties> smsPropertiesMap) {
        Map<String, SmsClient> smsClientMap = new LinkedHashMap<>(50);
        smsPropertiesMap.forEach((name, template) -> {
            switch (template.getSmsSupplier()) {
                case ALI:
                    smsClientMap.putIfAbsent(name, new DefaultAliCloudSmsClientImpl(template.getSmsProperties()));
                    break;
                case HUAWEI:
                    smsClientMap.putIfAbsent(name, new DefaultHuaweiCloudSmsClientImpl(template.getSmsProperties()));
                    break;
                case JINGDONG:
                    smsClientMap.putIfAbsent(name, new DefaultJingdongCloudSmsClientImpl(template.getSmsProperties()));
                    break;
                case QINIU:
                    smsClientMap.putIfAbsent(name, new DefaultQiniuCloudSmsClientImpl(template.getSmsProperties()));
                    break;
                default:
                    break;
            }
        });
        return smsClientMap;
    }

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
        private String regionId;
        /**
         * 短信签名
         */
        private String signName;
        /**
         * 短信模板
         */
        private String templateCode;
    }

    /**
     * 短信模板配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.multi-sms-template.template-properties")
    public static class SmsTemplateProperties {
        /**
         * 短信模板名称（同一短信供应商的如果有多个短信模板,模板名称不能重复，重复情况仅配置文件最后一个模板名称有效）
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
