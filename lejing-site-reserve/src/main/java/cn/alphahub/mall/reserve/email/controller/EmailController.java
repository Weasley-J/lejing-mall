package cn.alphahub.mall.reserve.email.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import static cn.alphahub.mall.email.EmailTemplate.MimeMessageDomain;
import static cn.alphahub.mall.email.EmailTemplate.SimpleMailMessageDomain;

/**
 * 邮件Controller
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-09 14:01
 */
@Slf4j
@RestController
@RequestMapping("/site/email")
public class EmailController {

    @Resource
    private EmailTemplate emailTemplate;

    /**
     * 发送给定的简单邮件消息
     *
     * @param message 简单邮件消息对象
     * @return ok
     */
    @Email(name = "EmailQQ")
    @PostMapping("/simple/send")
    public BaseResult<Void> sendSimpleEmail(@ModelAttribute(name = "message") @Validated SimpleMailMessageDomain message) {
        log.info("send simple email:{}", message);
        emailTemplate.send(message);
        return BaseResult.ok();
    }

    /**
     * 发送带附件的邮件消息
     *
     * @param message Mime邮件消息对象
     * @param file    文件
     * @return tips
     */
    @Email(name = "EmailOffice365")
    @PostMapping("/mime/send")
    public BaseResult<Void> sendMimeEmail(@ModelAttribute(name = "message") @Validated MimeMessageDomain message,
                                          @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        log.info("send mime email:{}", message);
        try {
            emailTemplate.send(message, file);
        } catch (MessagingException e) {
            log.error("domain:{},{}", message, e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }
}
