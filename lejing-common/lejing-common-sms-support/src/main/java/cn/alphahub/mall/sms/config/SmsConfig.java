package cn.alphahub.mall.sms.config;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.enums.SmsSupplier;
import cn.alphahub.mall.sms.impl.DefaultAliCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultHuaweiCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultJingdongCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultQiniuCloudSmsClientImpl;
import cn.alphahub.mall.sms.impl.DefaultTencentCloudSmsClientImpl;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static cn.alphahub.mall.sms.config.SmsConfig.MultipleSmsTemplateProperties;
import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;
import static cn.alphahub.mall.sms.config.SmsConfig.SmsTemplateProperties;
import static cn.alphahub.mall.sms.config.SmsConfig.ThreadPoolProperties;

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
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
@EnableConfigurationProperties({
        SmsProperties.class, SmsTemplateProperties.class,
        MultipleSmsTemplateProperties.class, ThreadPoolProperties.class
})
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
     * 短信模板配置map集合
     *
     * @param templateProperties         短信配置元数据
     * @param multiSmsTemplateProperties 多短信模板、多供应商配置元数据
     * @return 短信模板配置集合
     * @apiNote 初始化短信模板配置集合为50个（包含默认短信模板）
     */
    @Bean({"smsPropertiesMap"})
    public Map<String, SmsTemplateProperties> smsPropertiesMap(SmsTemplateProperties templateProperties, MultipleSmsTemplateProperties multiSmsTemplateProperties) {
        Map<String, SmsTemplateProperties> smsSupportMap = new LinkedHashMap<>(50);
        if (Objects.nonNull(templateProperties)) {
            templateProperties.setTemplateName(SMS.DEFAULT_TEMPLATE);
            String decorateTemplateName = decorateTemplateName(templateProperties.getSmsSupplier(), templateProperties.getTemplateName());
            smsSupportMap.put(decorateTemplateName, templateProperties);
            log.info("Loaded default sms template '{}'", decorateTemplateName);
        }
        if (CollUtil.isNotEmpty(multiSmsTemplateProperties.getTemplateProperties())) {
            for (SmsTemplateProperties templateProperty : multiSmsTemplateProperties.getTemplateProperties()) {
                String decorateTemplateName = decorateTemplateName(templateProperty.getSmsSupplier(), templateProperty.getTemplateName());
                smsSupportMap.putIfAbsent(decorateTemplateName, templateProperty);
                log.info("Loaded multiple sms template '{}'", decorateTemplateName);
            }
        }
        return smsSupportMap;
    }

    /**
     * 多模板、多供应商短信发送实例对象map集合
     *
     * @param smsPropertiesMap 短信模板配置集合
     * @return 多模板、多供应商短信发送实例对象集合
     */
    @Bean({"smsClientMap"})
    @DependsOn({"smsPropertiesMap"})
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
                case TENCENT:
                    smsClientMap.putIfAbsent(name, new DefaultTencentCloudSmsClientImpl(template.getSmsProperties()));
                    break;
                default:
                    break;
            }
        });
        return smsClientMap;
    }

    /**
     * 线程池
     *
     * @return thread pool executor
     */
    @Bean
    @ConditionalOnMissingBean(value = {ThreadPoolExecutor.class, Executor.class})
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
     * 短信配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.sms-properties")
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
         * 短信签名、短信签名id
         */
        private String signName;
        /**
         * 短信模板code、短信模板id
         */
        private String templateCode;
        /**
         * 短信sdk的appId，有就填，没有留空，如：腾讯云需要、阿里云不需要
         */
        private String appId;
    }

    /**
     * 短信模板配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms")
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
        @NestedConfigurationProperty
        private SmsProperties smsProperties;
    }

    /**
     * 多短信模板、多供应商配置元数据
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.multi-sms-templates")
    public static class MultipleSmsTemplateProperties {
        /**
         * 短信模板配置列表
         */
        private List<SmsTemplateProperties> templateProperties;
    }

    /**
     * 线程池配置参数
     */
    @Data
    @ConfigurationProperties(prefix = "spring.sms.thread")
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
