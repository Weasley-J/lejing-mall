package cn.alphahub.mall.email.support;

import cn.alphahub.mall.email.annotation.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 默认动态邮件参数支持实现
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-07 15:56
 */
@Component
public class DefaultMailSupportImpl implements MailSupport {

    @Resource
    private JavaMailSender mailSender;


    @Override
    @Email(templateName = "EmailQQ")
    public void send() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("");
        simpleMessage.setReplyTo("");
        simpleMessage.setTo("");
        simpleMessage.setCc("");
        simpleMessage.setBcc("");
        simpleMessage.setSentDate(new Date());
        simpleMessage.setSubject("");
        simpleMessage.setText("");
        mailSender.send(simpleMessage);
    }
}
