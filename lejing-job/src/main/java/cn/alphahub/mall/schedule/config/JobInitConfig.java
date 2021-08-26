package cn.alphahub.mall.schedule.config;

import cn.alphahub.mall.schedule.job.domain.SysJob;
import cn.alphahub.mall.schedule.job.service.SysJobService;
import cn.alphahub.mall.schedule.quartz.service.QuartzJobService;
import cn.alphahub.mall.schedule.quartz.util.JobBeanUtil;
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
public class JobInitConfig implements ApplicationRunner {

    @Resource
    private SysJobService jobService;
    @Resource
    private QuartzJobService quartzJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SysJob> jobs = jobService.list();
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        jobs.forEach(job -> {
            quartzJobService.createScheduleJobCron(JobBeanUtil.getQuartzBean(job));
            System.err.println(">>>>>>>>>>>>>>>>>>>>持久层定时任务[" + job.getJobName() + "]启动成功,备注信息: " + job.getRemark());
        });
    }
}
