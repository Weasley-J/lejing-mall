package cn.alphahub.mall.email.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class EmailConfigTest {

    @Resource
    private EmailConfig.EmailTemplateProperties emailTemplateProperties;
    @Resource
    private Map<String, MailProperties> emailPropertiesMap;
    @Resource
    private Environment environment;

    @BeforeEach
    void setUp() {
        System.err.println("----------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("----------------------");
    }

    @Test
    void getEmailPropertiesMap() {
        Map<String, MailProperties> map = this.emailPropertiesMap;
        map.forEach((s, mailProperties) -> {
            System.out.println(s);
        });
    }

    @Test
    void getEmailTemplates() {
        String property = environment.getProperty("spring.mail.email-templates");
        List<EmailConfig.EmailProperties> templates = emailTemplateProperties.getEmailTemplates();
        templates.forEach(k -> System.err.println("k = " + k));
    }
}