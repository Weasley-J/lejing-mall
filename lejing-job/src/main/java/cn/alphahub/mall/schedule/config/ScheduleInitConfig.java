package cn.alphahub.mall.schedule.config;

import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.module.internal.InitializeAllScheduleTask;
import cn.hutool.core.date.DateUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * SpringBoot启动后加载并执行持久层定时任务
 *
 * @author lwj
 */
@Component
public class ScheduleInitConfig implements ApplicationRunner {

    @Resource
    private QuartzCoreService quartzCoreService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Date startDate = DateUtil.offsetMinute(new Date(), 3).toJdkDate();
        String startDateStr = DateUtil.formatDateTime(startDate);
        System.err.println("\tPersistence tasks will start initialization at '" + startDateStr + "'");

        QuartzParam param = new QuartzParam();
        param.setJobName("InitializeAllJdbcScheduledTask");
        param.setJobGroup("InitializeGroup");
        param.setJobClass(InitializeAllScheduleTask.class.getName());
        param.setJobDescription("初始化所有持久层定时任务");
        param.setStatus(1);
        param.setStartTime(startDate);

        quartzCoreService.createSimpleScheduleJob(param);
    }
}
