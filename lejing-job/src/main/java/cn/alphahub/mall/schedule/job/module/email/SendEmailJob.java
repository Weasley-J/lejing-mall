package cn.alphahub.mall.schedule.job.module.email;

import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import cn.alphahub.mall.schedule.constant.ScheduleConstant;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送邮件的定时任务
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-13
 */
@Slf4j
@Component
public class SendEmailJob extends QuartzJobBean {

    @Resource
    private EmailTemplate emailTemplate;

    @Override
    @SneakyThrows
    @Email(name = "EmailOffice365")
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        String emailJsonString = dataMap.getString(ScheduleConstant.JOB_PARAM_KEY);
        log.warn("send email job:{}", JSONUtil.toJsonStr(emailJsonString));
        if (StringUtils.isNoneBlank(emailJsonString)) {
            EmailTemplate.MimeMessageDomain message = JSONUtil.toBean(emailJsonString, EmailTemplate.MimeMessageDomain.class);
            emailTemplate.send(message, null);
        }
    }
}
