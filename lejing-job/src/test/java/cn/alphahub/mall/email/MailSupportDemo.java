package cn.alphahub.mall.email;

import cn.alphahub.mall.email.annotation.Email;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    private EmailTemplate.SimpleMailMessageDomain getSimpleMessage() {
        EmailTemplate.SimpleMailMessageDomain simpleMessage = new EmailTemplate.SimpleMailMessageDomain();
        simpleMessage.setTo("1432689025@qq.com");
        simpleMessage.setSentDate(LocalDateTime.now());
        simpleMessage.setSubject("测试模板发送邮件");
        simpleMessage.setText("这是一个测试模板发送邮件的案例");
        return simpleMessage;
    }

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

    /**
     * 使用微软Office365S邮箱发送
     */
    @Email(name = "EmailOffice365")
    public void emailOffice365Send() {
        try {
            emailTemplate.send(getSimpleMessage());
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }
}
