package cn.alphahub.mall.reserve.email.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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
    @PostMapping("/simple/send")
    public BaseResult<Void> sendSimpleEmail() {
        log.info("send simple email.");
        SimpleMailMessageDomain simpleMessage = new SimpleMailMessageDomain();
        simpleMessage.setTo("1432689025@qq.com");
        simpleMessage.setSentDate(new Date());
        simpleMessage.setSubject("咏鹅");
        simpleMessage.setText(" 鹅鹅鹅，曲项向天歌。\n" +
                "\n" +
                "白毛浮绿水，红掌拨清波。 ");
        emailTemplate.send(simpleMessage);
        return BaseResult.ok();
    }

    /**
     * send mime email
     *
     * @return ok
     */
    @Email(name = "EmailOffice365")
    @PostMapping("/mime/send")
    public BaseResult<Void> sendMimeEmail() {
        log.info("send mime email.");
        SimpleMailMessageDomain simpleMessage = new SimpleMailMessageDomain();
        simpleMessage.setTo("1432689025@qq.com");
        simpleMessage.setSentDate(new Date());
        simpleMessage.setSubject("咏鹅");
        simpleMessage.setText(" 鹅鹅鹅，曲项向天歌。\n" +
                "\n" +
                "白毛浮绿水，红掌拨清波。 ");
        emailTemplate.send(simpleMessage);
        return BaseResult.ok();
    }
}
