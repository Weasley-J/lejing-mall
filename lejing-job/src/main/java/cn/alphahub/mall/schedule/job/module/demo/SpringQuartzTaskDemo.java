package cn.alphahub.mall.schedule.job.module.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 模拟Quartz定时任务所重复执行的的任务类
 * <p>与springboot整合: Job class must abstract class QuartzJobBean,该类已经实现了Job interface
 *
 * @author lwj
 */
@Slf4j
public class SpringQuartzTaskDemo extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap map = context.getJobDetail().getJobDataMap();
        System.err.println("\nHello, I am quartz schedule job, 定时任务: " + jobKey + ", job-data: " + JSONUtil.toJsonStr(map) + ", executing at: " + DateUtil.formatDateTime(new Date()));
    }
}
