package cn.alphahub.mall.email;

import cn.alphahub.mall.email.config.EmailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * email config tests
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-07 12:32
 */
@SpringBootTest
public class EmailConfigTests {

    @Resource
    private EmailConfig emailConfig;
    @Resource
    private EmailConfig.EmailTemplateProperties emailTemplateProperties;

    @Test
    void contextLoads() {
        List<EmailConfig.EmailProperties> templates = emailTemplateProperties.getEmailTemplates();
        System.out.println("templates = " + templates);
    }
}
