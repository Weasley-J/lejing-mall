package cn.alphahub.mall.schedule.config;

import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * SpringBoot启动后加载并执行持久层定时任务
 *
 * @author lwj
 */
@Slf4j
@Component
public class ScheduleJobInitConfig implements ApplicationRunner {

    @Resource
    private QuartzJobService quartzJobService;

    @Resource
    private QuartzCoreService quartzCoreService;

    @Resource
    private ScheduleConvertor scheduleConvertor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<QuartzJob> jobs = quartzJobService.list();
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        jobs.forEach(job -> {
            QuartzParam quartzParam = scheduleConvertor.toQuartzParam(job);
            quartzCoreService.createCronScheduleJob(quartzParam);
            System.err.println("调度任务参数:'" + quartzParam + "'");
        });
    }
}
