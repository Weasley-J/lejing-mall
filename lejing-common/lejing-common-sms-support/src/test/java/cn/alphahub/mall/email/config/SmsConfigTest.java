package cn.alphahub.mall.email.config;

import cn.alphahub.mall.email.SmsSupport;
import cn.alphahub.mall.email.SmsTemplate;
import cn.alphahub.mall.email.impl.DefaultAliCloudSmsSupportImpl;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SmsConfigTest {

    @Resource
    private SmsConfig.SmsProperties smsProperties;

    @Resource
    private SmsConfig.MultipleSmsTemplateProperties multipleSmsTemplateProperties;

    @Resource
    private Map<String, SmsSupport> smsSupportMap;

    @BeforeEach
    void setUp() {
        System.err.println("----------------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("----------------------------");
    }

    @Test
    void testSmsProperties() {
        System.err.println(JSONUtil.toJsonPrettyStr(smsProperties));
    }

    @Test
    void testMultipleSmsTemplateProperties() {
        List<SmsConfig.SmsTemplateProperties> templateProperties = multipleSmsTemplateProperties.getTemplateProperties();
        System.err.println(templateProperties + "\n");
        for (SmsConfig.SmsTemplateProperties templateProperty : templateProperties) {
            System.out.println(JSONUtil.toJsonPrettyStr(templateProperty));
        }
    }

    @Test
    @SneakyThrows
    void testInstance() {
        Class<DefaultAliCloudSmsSupportImpl> DefaultAliCloudSmsSupportClass = DefaultAliCloudSmsSupportImpl.class;
        Class<SmsTemplate> smsTemplateClass = SmsTemplate.class;

        DefaultAliCloudSmsSupportImpl defaultAliCloudSmsSupport = DefaultAliCloudSmsSupportClass.getDeclaredConstructor().newInstance();
        SmsTemplate smsTemplate = smsTemplateClass.getDeclaredConstructor().newInstance();

        System.out.println("DefaultAliCloudSmsSupportClass是SmsSupport的实例吗：" + (defaultAliCloudSmsSupport instanceof SmsSupport));
        System.out.println("SmsTemplate是SmsSupport的实例吗：" + (smsTemplate instanceof SmsSupport));
    }

    @Test
    void smsSupportMapTest() {
        Map<String, SmsSupport> smsSupportMap = this.smsSupportMap;
        smsSupportMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
            if ("ALI_CLOUD:DEFAULT".equals(k)) {
                Object send = v.send("123456", "18114882681");
                System.err.println(send.toString());
            }
        });
    }
}
