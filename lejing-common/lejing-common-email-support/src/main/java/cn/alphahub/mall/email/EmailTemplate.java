package cn.alphahub.mall.email;

import cn.alphahub.mall.email.aspect.EmailAspect;
import cn.hutool.core.io.FileUtil;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * 邮件模板方法默认实现
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
     * 发送给定的简单邮件消息
     *
     * @param domain the message to send
     * @throws MailException Base class for all mail exceptions
     */
    public void send(@Valid SimpleMailMessageDomain domain) throws MailException {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(this.getMailProperties().getUsername());
        simpleMessage.setTo(domain.getTo());
        simpleMessage.setSentDate(Objects.nonNull(domain.getSentDate()) ? Date.from(domain.getSentDate().atZone(ZoneId.systemDefault()).toInstant()) : new Date());
        simpleMessage.setSubject(domain.getSubject());
        simpleMessage.setText(domain.getText());
        JavaMailSender mailSender = this.getMailSender();
        mailSender.send(simpleMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param domain metadata of message to send
     * @param file   Nullable, support for spring MVC upload file received in the request, can be null.
     * @throws MailException Base class for all mail exceptions
     */
    public void send(@Valid MimeMessageDomain domain, @Nullable MultipartFile file) throws MessagingException {
        JavaMailSender mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(this.getMailProperties().getUsername());
        helper.setTo(domain.getTo());
        helper.setSentDate(Objects.nonNull(domain.getSentDate()) ? Date.from(domain.getSentDate().atZone(ZoneId.systemDefault()).toInstant()) : new Date());
        helper.setSubject(domain.getSubject());
        helper.setText(domain.getText(), true);
        if (Objects.nonNull(file) && !file.isEmpty()) {
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename(), "The attachment file name (including file suffix) cannot be empty"), file);
        }
        if (StringUtils.isNoneBlank(domain.getFilepath())) {
            File newFile = new File(domain.getFilepath());
            helper.addAttachment(FileUtil.getName(domain.getFilepath()), newFile);
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
         * 收件人的邮箱
         */
        @NotBlank(message = "收件人邮箱不能为空")
        private String to;
        /**
         * 邮件发送日期, 默认当前时刻: {@code new Date()} 提交格式: yyyy-MM-dd HH:mm:ss
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime sentDate;
        /**
         * 邮件主题、邮件标题
         */
        @NotBlank(message = "邮件主题不能为空")
        private String subject;
        /**
         * 邮件正文、邮件内容
         */
        @NotBlank(message = "邮件正文不能为空")
        private String text;
    }

    /**
     * 带附件邮件消息对象
     * <p>支持附件：图片、文件等资源
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MimeMessageDomain implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 收件人的邮箱
         */
        @NotBlank(message = "收件人邮箱不能为空")
        private String to;
        /**
         * 邮件发送日期, 默认当前时刻: {@code new Date()} 提交格式: yyyy-MM-dd HH:mm:ss
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime sentDate;
        /**
         * 邮件主题、邮件标题
         */
        @NotBlank(message = "邮件主题不能为空")
        private String subject;
        /**
         * 邮件正文、邮件内容 （可以是html字符串）
         */
        @NotBlank(message = "邮件正文不能为空")
        private String text;
        /**
         * 附件文件的路径 （没有附件文件不用还传）
         */
        private String filepath;
    }
}
