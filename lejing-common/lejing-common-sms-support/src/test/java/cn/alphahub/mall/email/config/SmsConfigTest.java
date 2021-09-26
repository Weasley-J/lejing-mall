package cn.alphahub.mall.email.config;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SmsConfigTest {

    @Resource
    private SmsConfig.SmsProperties smsProperties;

    @Resource
    private SmsConfig.MultipleSmsTemplateProperties multipleSmsTemplateProperties;

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
}
