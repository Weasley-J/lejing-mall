package cn.alphahub.mall.reserve.email.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

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
     * @return ok
     */
    @Email(name = "Email163")
    @PostMapping("/simple/send")
    public BaseResult<Void> sendSimpleEmail(@RequestBody @Validated SimpleMailMessageDomain message) {
        log.info("send simple email:{}", message);
        SimpleMailMessageDomain simpleMessage = new SimpleMailMessageDomain();
        simpleMessage.setTo("1432689025@qq.com");
        simpleMessage.setSentDate(new Date());
        simpleMessage.setSubject("咏鹅");
        simpleMessage.setText(" 鹅鹅鹅，曲项向天歌。\n" +
                "\n" +
                "白毛浮绿水，红掌拨清波。 ");
        if (Objects.nonNull(message)) {
            emailTemplate.send(message);
        } else {
            emailTemplate.send(simpleMessage);
        }
        return BaseResult.ok();
    }

    /**
     * 发送给定的 JavaMail MIME 消息
     *
     * @return ok
     */
    @Email(name = "EmailOffice365")
    @PostMapping("/mime/send")
    public BaseResult<Void> sendMimeEmail(@RequestBody @Validated MimeMessageDomain message, @RequestPart(name = "file") MultipartFile file) {
        log.info("send mime email:{}", message);
        MimeMessageDomain messageDomain = new MimeMessageDomain();
        messageDomain.setTo("1432689025@qq.com");
        messageDomain.setSentDate(new Date());
        messageDomain.setSubject("咏鹅");
        messageDomain.setText(" 鹅鹅鹅，曲项向天歌。\n" +
                "\n" +
                "白毛浮绿水，红掌拨清波。 ");
        messageDomain.setFilepath("C:\\Users\\liuwe\\Desktop\\QQ图片20210909202504.jpg");
        try {
            if (Objects.nonNull(message)) {
                emailTemplate.send(message, file);
            } else {
                emailTemplate.send(messageDomain, null);
            }
        } catch (MessagingException e) {
            log.error("domain:{},{}", message, e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }
}
