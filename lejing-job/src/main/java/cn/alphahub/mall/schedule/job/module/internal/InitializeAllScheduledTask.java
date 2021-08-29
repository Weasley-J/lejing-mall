package cn.alphahub.mall.schedule.job.module.internal;

import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import cn.hutool.extra.spring.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 项目启动后完成,3min后初始化所有持久层定时任务
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/29
 */
public class InitializeAllScheduledTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        QuartzJobService quartzJobService = SpringUtil.getBean(QuartzJobService.class);
        QuartzCoreService quartzCoreService = SpringUtil.getBean(QuartzCoreService.class);
        ScheduleConvertor scheduleConvertor = SpringUtil.getBean(ScheduleConvertor.class);

        List<QuartzJob> jobs = quartzJobService.list();
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }

        jobs.forEach(job -> {
            QuartzParam quartzParam = scheduleConvertor.toQuartzParam(job);
            quartzCoreService.createCronScheduleJob(quartzParam);
            System.err.println("\tScheduling task parameters '" + JSONUtil.toJsonStr(quartzParam) + "'");
        });
    }
}
