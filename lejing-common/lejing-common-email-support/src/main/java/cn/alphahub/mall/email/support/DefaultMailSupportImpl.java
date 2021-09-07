package cn.alphahub.mall.email.support;
import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private JavaMailSender javaMailSender;


    @Override
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
        javaMailSender.send(simpleMessage);
    }
}
