package cn.alphahub.mall.email;

import cn.alphahub.mall.email.aspect.EmailAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Resource
    private MailProperties defaultMailProperties;

    /**
     * 获取邮件是发送实例
     *
     * @return JavaMailSender
     */
    private JavaMailSender getMailSender() {
        JavaMailSender mailSender = emailAspect.javaMailSender();
        if (Objects.isNull(mailSender)) {
            return this.defaultJavaMailSender;
        }
        return mailSender;
    }

    /**
     * 获取电子邮件支持的配置属性
     *
     * @return MailProperties
     */
    private MailProperties getMailProperties() {
        MailProperties properties = emailAspect.mailProperties();
        if (Objects.isNull(properties)) {
            return this.defaultMailProperties;
        }
        return properties;
    }

    /**
     * Send the given JavaMail MIME message.
     *
     * @param mimeMessage message to send
     * @throws MailException Base class for all mail exceptions
     */
    public void send(MimeMessage mimeMessage) throws Exception {
        JavaMailSender mailSender = this.getMailSender();
        mimeMessage.setFrom(this.getMailProperties().getUsername());
        mailSender.send(mimeMessage);
    }

    /**
     * Send the given simple mail message.
     *
     * @param simpleMessage the message to send
     * @throws MailException Base class for all mail exceptions
     */
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        JavaMailSender mailSender = this.getMailSender();
        simpleMessage.setFrom(this.getMailProperties().getUsername());
        mailSender.send(simpleMessage);
    }
}
