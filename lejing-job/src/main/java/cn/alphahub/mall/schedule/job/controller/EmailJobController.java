package cn.alphahub.mall.schedule.job.controller;

import cn.alphahub.mall.common.core.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static cn.alphahub.mall.email.EmailTemplate.MimeMessageDomain;

/**
 * 获取邮件参数Controller
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-13
 */
@RestController
@RequestMapping("/schedule/job")
public class EmailJobController {

    /**
     * 此接口用来获取创建定时发送邮件的任务参数,即发送邮件的入参
     * <p>供quartz执行任发送邮件务时使用从JobDataMap中获取使用
     *
     * @return 带附件邮件消息对象
     */
    @GetMapping("/email/params")
    public Result<MimeMessageDomain> getSendEmailParams() {
        MimeMessageDomain domain = new MimeMessageDomain();
        domain.setTo("收件人的邮箱");
        domain.setSentDate(LocalDateTime.now());
        domain.setSubject("邮件标题");
        domain.setText("邮件内容");
        domain.setFilepath("附件文件的路径,没有附件文件不用还传,本服务邮件job全类名为'cn.alphahub.mall.schedule.job.module.email.SendEmailJob'");
        return Result.ok("发送邮件的参数", domain);
    }
}
