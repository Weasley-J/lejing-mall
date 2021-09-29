package cn.alphahub.mall.sms.config;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.impl.DefaultAliCloudSmsClientImpl;
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
    private Map<String, SmsClient> smsClientMap;

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
        Class<DefaultAliCloudSmsClientImpl> DefaultAliCloudSmsSupportClass = DefaultAliCloudSmsClientImpl.class;
        Class<SmsTemplate> smsTemplateClass = SmsTemplate.class;

        DefaultAliCloudSmsClientImpl defaultAliCloudSmsSupport = DefaultAliCloudSmsSupportClass.getDeclaredConstructor().newInstance();
        SmsTemplate smsTemplate = smsTemplateClass.getDeclaredConstructor().newInstance();

        System.out.println("DefaultAliCloudSmsSupportClass是SmsSupport的实例吗：" + (defaultAliCloudSmsSupport instanceof SmsClient));
        System.out.println("SmsTemplate是SmsClient的实例吗：" + (smsTemplate instanceof SmsClient));
    }

    @Test
    void smsSupportMapTest() {
        Map<String, SmsClient> smsClientMap = this.smsClientMap;
        smsClientMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
            if ("ALI_CLOUD:DEFAULT".equals(k)) {
                Object send = v.send("123456", "18114882681");
                System.err.println(send.toString());
            }
        });
    }

    @Test
    void smsSupportMapTencentTest() {
        Map<String, SmsClient> smsClientMap = this.smsClientMap;
        smsClientMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
            if ("TENCENT_CLOUD:内容短信模板".equals(k)) {
                //Object send = v.send("123456,30", "18114882681");
                Object send = v.send("", "18114882681");
                System.err.println(send.toString());
            }
        });
    }

    @Test
    void smsSupportMapJdTest() {
        Map<String, SmsClient> smsClientMap = this.smsClientMap;
        smsClientMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
            if ("JINGDONG_CLOUD:京东云短信验证码模板".equals(k)) {
                Object send = v.send("123456,30", "18114882681");
                //Object send = v.send("", "18114882681");
                //Object send = v.send("123456,30", "");
                System.err.println(send.toString());
            }
        });
    }
}
