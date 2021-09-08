package cn.alphahub.mall.email;

import cn.alphahub.mall.email.aspect.EmailAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * 邮件模板方法接口
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-07 10:44
 */
@Slf4j
@Component
public class EmailTemplate {

    @Resource
    private EmailAspect emailAspect;

    @Resource
    private JavaMailSender defaultJavaMailSender;

    /**
     * 获取邮件是发送实例
     *
     * @return JavaMailSender
     */
    private JavaMailSender getMailSender() {
        JavaMailSender mailSender = emailAspect.getJavaMailSender();
        if (Objects.isNull(mailSender)) {
            return this.defaultJavaMailSender;
        }
        return mailSender;
    }

    public void send(MimeMessage mimeMessage) throws MailException {
        JavaMailSender mailSender = this.getMailSender();
        mailSender.send(mimeMessage);
    }

    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        JavaMailSender mailSender = this.getMailSender();
        mailSender.send(mimeMessagePreparator);
    }

    public void send(SimpleMailMessage simpleMessage) throws MailException {
        JavaMailSender mailSender = this.getMailSender();
        mailSender.send(simpleMessage);
    }
}
