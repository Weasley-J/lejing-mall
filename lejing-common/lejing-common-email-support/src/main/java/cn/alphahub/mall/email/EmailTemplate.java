package cn.alphahub.mall.email;

import cn.alphahub.mall.email.aspect.EmailAspect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
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
@Validated
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
     * SimpleMailMessageDomain -> SimpleMailMessage
     *
     * @param domain 简单邮件消息对象
     * @return SimpleMailMessage
     */
    private SimpleMailMessage copyTo(@NotNull SimpleMailMessageDomain domain) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(domain.getTo());
        message.setSentDate(Objects.nonNull(domain.getSentDate()) ? domain.getSentDate() : new Date());
        message.setSubject(domain.getSubject());
        message.setText(domain.getText());
        return message;
    }

    /**
     * Send the given simple mail message.
     *
     * @param message the message to send
     * @throws MailException Base class for all mail exceptions
     */
    public void send(SimpleMailMessageDomain message) throws MailException {
        SimpleMailMessage simpleMessage = copyTo(message);
        simpleMessage.setFrom(this.getMailProperties().getUsername());
        JavaMailSender mailSender = this.getMailSender();
        mailSender.send(simpleMessage);
    }

    /**
     * Send the given JavaMail MIME message.
     *
     * @param domain metadata of message to send
     * @param file   Nullable, support for spring MVC upload file received in the request, can be null.
     * @throws MailException Base class for all mail exceptions
     */
    public void send(MimeMessageDomain domain, @Nullable MultipartFile file) throws MessagingException {
        JavaMailSender mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(this.getMailProperties().getUsername());
        helper.setTo(domain.getTo());
        helper.setSentDate(domain.getSentDate());
        helper.setSubject(domain.getSubject());
        helper.setText(domain.getText(), true);
        if (Objects.nonNull(file) && !file.isEmpty()) {
            helper.addAttachment(domain.getAttachmentFilename(), file);
        }
        if (StringUtils.isNoneBlank(domain.getAttachmentFilename(), domain.getFilepath())) {
            helper.addAttachment(domain.getAttachmentFilename(), new File(domain.getFilepath()));
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 简单邮件消息对象
     * <p>支持文本消息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleMailMessageDomain implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 收件人的邮箱,可以是一个或多个
         */
        private String to;
        /**
         * 邮件发送日期, 默认当前时刻: new Date()
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date sentDate;
        /**
         * 邮件主题、邮件标题
         */
        private String subject;
        /**
         * 邮件正文、邮件内容
         */
        private String text;
    }

    /**
     * Mime邮件消息对象
     * <p>支持附件：图片、文件等资源
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MimeMessageDomain implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 收件人的邮箱,可以是一个或多个
         */
        private String to;
        /**
         * 邮件发送日期, 默认当前时刻: new Date()
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date sentDate;
        /**
         * 邮件主题、邮件标题
         */
        private String subject;
        /**
         * 邮件正文、邮件内容 （可以是html字符串）
         */
        private String text;
        /**
         * 将出现在邮件中的附件文件名称 （有附件时才传过来）
         */
        private String attachmentFilename;
        /**
         * 附件文件的路径 （有附件时才传过来）
         */
        private String filepath;
    }
}
