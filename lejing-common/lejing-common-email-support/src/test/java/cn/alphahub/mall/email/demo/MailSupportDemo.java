package cn.alphahub.mall.email.demo;

import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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
public class MailSupportDemo {

    @Resource
    private EmailTemplate emailTemplate;

    /**
     * 默认配置邮箱发送
     */
    public void defaultSend() {
        try {
            emailTemplate.send(getSimpleMessage());
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * QQ邮箱发送
     */
    @Email(name = "EmailQQ")
    public void qqEmailSend() {
        try {
            emailTemplate.send(getSimpleMessage());
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 网易163邮箱发送
     */
    @Email(name = "Email163")
    public void email163Send() {
        try {
            emailTemplate.send(getSimpleMessage());
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    private SimpleMailMessage getSimpleMessage() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("66666666@qq.com");
        simpleMessage.setReplyTo("");
        simpleMessage.setTo("88888888@qq.com");
        simpleMessage.setCc("");
        simpleMessage.setBcc("");
        simpleMessage.setSentDate(new Date());
        simpleMessage.setSubject("subject");
        simpleMessage.setText("text");
        return simpleMessage;
    }
}
