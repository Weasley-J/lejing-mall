package cn.alphahub.mall.schedule.core.service.impl;

import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cn.alphahub.mall.schedule.constant.ScheduleConstant.TriggerStateEnum;

/**
 * quartz任务调度核心方法实现
 *
 * @author lwj
 * @version 1.0.1
 */
@Slf4j
@Service
public class DefaultQuartzCoreServiceImpl implements QuartzCoreService {

    @Resource
    private Scheduler scheduler;

    @Override
    public boolean createSimpleScheduleJob(QuartzParam param) {
        log.info("创建Simple定时任务:{}", JSONUtil.toJsonStr(param));
        // 获取到定时任务的执行类  必须是全限定类名, 定时任务类需要是job类的具体实现QuartzJobBean是job的抽象类
        Class<? extends Job> jobClass = getJobClass(param.getJobClass());
        if (Objects.isNull(jobClass)) {
            log.warn("任务实体类全限定类名为空");
            return false;
        }

        // 构建定时任务信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(getJobKey(param))
                .usingJobData(param.getJobDataMap())
                .withDescription(param.getJobDescription())
                .build();

        // 设置定时任务执行方式
        SimpleScheduleBuilder simpleScheduleBuilder;
        if (ObjectUtils.isEmpty(param.getInterval())) {
            //单次触发
            simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        } else {
            //循环触发
            simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(param.getInterval());
        }

        // 构建触发器trigger
        Trigger trigger;
        if (ObjectUtils.isEmpty(param.getInterval())) {
            //单次触发
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(param))
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(param.getStartTime())
                    .usingJobData(param.getJobDataMap())
                    .build();
        } else {
            //循环触发
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(param))
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(param.getStartTime())
                    .endAt(param.getEndTime())
                    .usingJobData(param.getJobDataMap())
                    .build();
        }

        try {
            deleteJobIfExists(scheduler, param);
            scheduler.scheduleJob(jobDetail, trigger);
            handlingJobStatus(scheduler, param);
        } catch (SchedulerException e) {
            log.error("param: {}, exception: {}", JSONUtil.toJsonStr(param), e.getLocalizedMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public boolean createCronScheduleJob(QuartzParam param) {
        log.info("创建Cron定时任务:{}", JSONUtil.toJsonStr(param));
        // 获取到定时任务的执行类  必须是类的绝对路径名称, 定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类
        Class<? extends Job> jobClass = getJobClass(param.getJobClass());
        if (Objects.isNull(jobClass)) {
            log.warn("任务实体类全限定类名为空！");
            return false;
        }

        try {
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(getJobKey(param))
                    .usingJobData(param.getJobDataMap())
                    .withDescription(param.getJobDescription())
                    .build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCronExpression());
            scheduleBuilder = handlingCronScheduleMisfirePolicy(scheduleBuilder, param.getMisfirePolicy());
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(param))
                    .usingJobData(param.getJobDataMap())
                    .withSchedule(scheduleBuilder)
                    .build();

            deleteJobIfExists(scheduler, param);
            scheduler.scheduleJob(jobDetail, trigger);
            handlingJobStatus(scheduler, param);

            return true;
        } catch (SchedulerException e) {
            log.error("param:{}, scheduler-exception:{}", JSONUtil.toJsonStr(param), e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public boolean pauseScheduleJob(String jobName, String jobGroup) {
        log.info("暂停定时任务:{},{}", jobGroup, jobGroup);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("jobName:{}, jobGroup:{}, SchedulerException:{}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public boolean resumeScheduleJob(String jobName, String jobGroup) {
        log.info("恢复定时任务:{},{}", jobGroup, jobGroup);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public void updateSimpleScheduleJob(QuartzParam param) {
        log.info("更新Simple定时任务:{}", JSONUtil.toJsonStr(param));
        //获取到对应任务的触发器
        TriggerKey triggerKey = getTriggerKey(param);
        // 设置定时任务执行方式
        SimpleScheduleBuilder simpleScheduleBuilder;
        if (param.getInterval() == null) {
            //单次
            simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        } else {
            //循环
            simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(param.getInterval());
        }
        // 构建触发器trigger
        Trigger trigger = TriggerBuilder.newTrigger().build();
        if (Objects.isNull(param.getInterval())) {
            //单次执行
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(param))
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(param.getStartTime())
                    .usingJobData(param.getJobDataMap())
                    .build();
        } else {
            //循环执行
            TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(param))
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(param.getStartTime())
                    .endAt(param.getEndTime())
                    .usingJobData(param.getJobDataMap())
                    .build();
        }
        //重置对应的job
        try {
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("{}, {}", JSONUtil.toJsonStr(param), e.getLocalizedMessage(), e);
        }
    }

    @Override
    public boolean updateCronScheduleJob(QuartzParam param) {
        log.info("更新Cron定时任务:{}", JSONUtil.toJsonStr(param));
        try {
            //获取到对应任务的触发器
            TriggerKey triggerKey = getTriggerKey(param);

            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCronExpression());
            scheduleBuilder = handlingCronScheduleMisfirePolicy(scheduleBuilder, param.getMisfirePolicy());

            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .usingJobData(param.getJobDataMap())
                    .build();

            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("{}, {}", JSONUtil.toJsonStr(param), e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteScheduleJob(String jobName, String jobGroup) {
        log.info("从调度器当中删除定时任务:{},{}", jobGroup, jobGroup);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public String getScheduleJobStatus(String jobName, String jobGroup) {
        log.info("获取任务状态:{},{}", jobGroup, jobGroup);
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            String name = state.name();
            TriggerStateEnum stateEnum = TriggerStateEnum.getEnum(name);
            return stateEnum.getName();
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public Trigger.TriggerState getScheduleJobStatus(QuartzParam param) {
        TriggerKey triggerKey = getTriggerKey(param);
        try {
            return scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            log.error("get-schedule-job-status:{}", e.getLocalizedMessage(), e);
            return Trigger.TriggerState.NONE;
        }
    }

    @Override
    public boolean isScheduleJobExists(String jobName, String jobGroup) {
        log.info("判断任务是否存在:{},{}", jobGroup, jobGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public boolean deleteGroupJob(String jobGroup) {
        log.info("组删除定时任务:{},{}", jobGroup, jobGroup);
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            List<JobKey> jobKeyList = new ArrayList<>(jobKeySet);
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            log.error("{}, {}", jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public boolean batchDeleteGroupJob(List<JobKey> jobKeyList) {
        log.warn("batch delete group job:{}", JSONUtil.toJsonStr(jobKeyList));
        try {
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            log.error("{}, {}", JSONUtil.toJsonStr(jobKeyList), e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup) {
        log.info("batch query group job:{}", JSONUtil.toJsonStr(jobKeyList));
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            jobKeyList.addAll(jobKeySet);
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobGroup, JSONUtil.toJsonStr(jobKeyList), e.getLocalizedMessage(), e);
        }
    }

    @Override
    public boolean executeAtNow(String jobName, String jobGroup) {
        log.info("立即运行一次定时任务:{},{}", jobGroup, jobGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("{}, {}, {}", jobName, jobGroup, e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    @Override
    public void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }

    @Override
    public Class<? extends Job> getJobClass(String targetClass) {
        if (StringUtils.isBlank(targetClass)) {
            log.error("任务执行类全限定类名不能为空，你必须定义一个类作为Quartz定时任务的业务类: {}", targetClass);
            return null;
        }
        try {
            Class<?> jobClass = Class.forName(targetClass);
            Object newInstance = jobClass.getDeclaredConstructor().newInstance();
            if (newInstance instanceof Job) {
                Job instance = (Job) newInstance;
                return instance.getClass();
            } else {
                log.error("你任务类'{}'没有实现'org.quartz.Job'接口, 不支持此类型", targetClass);
                return null;
            }
        } catch (Exception e) {
            log.error("Exception: {}; target class: {}", e.getLocalizedMessage(), targetClass, e);
            return null;
        }
    }
}
