package cn.alphahub.mall.schedule.quartz.service.impl;

import cn.alphahub.mall.schedule.quartz.entity.QuartzBean;
import cn.alphahub.mall.schedule.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Quartz任务调度实现类
 *
 * @author lwj
 */
@Slf4j
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建定时任务Simple
     * quartzBean.getInterval()==null表示单次提醒，
     * 否则循环提醒（quartzBean.getEndTime()!=null）
     *
     * @param quartzBean Quartz参数实体类
     */
    @Override
    public Boolean createScheduleJobSimple(QuartzBean quartzBean) {
        // 获取到定时任务的执行类  必须是全限定类名
        // 定时任务类需要是job类的具体实现QuartzJobBean是job的抽象类。
        Class<? extends Job> jobClass = getFullJobClassName(quartzBean);
        if (Objects.isNull(jobClass)) {
            log.warn("任务实体类全限定类名为空！");
            return false;
        }
        // 构建定时任务信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(quartzBean.getJobName(), ObjectUtils.isNotEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
                .setJobData(quartzBean.getJobDataMap())
                .build();
        // 设置定时任务执行方式
        SimpleScheduleBuilder simpleScheduleBuilder;
        if (ObjectUtils.isEmpty(quartzBean.getInterval())) {
            //单次
            simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            //循环
        } else {
            simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(quartzBean.getInterval());
        }
        // 构建触发器trigger
        Trigger trigger;
        if (ObjectUtils.isEmpty(quartzBean.getInterval())) {
            //单次
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzBean.getJobName(), ObjectUtils.isNotEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(quartzBean.getStartTime())
                    .build();
        } else {
            //循环
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzBean.getJobName(), ObjectUtils.isNotEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(quartzBean.getStartTime())
                    .endAt(quartzBean.getEndTime())
                    .build();
        }
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 创建定时任务Cron
     * 定时任务创建之后默认启动状态
     *
     * @param quartzBean 定时任务信息类
     */
    @Override
    public Boolean createScheduleJobCron(QuartzBean quartzBean) {
        //获取到定时任务的执行类  必须是类的绝对路径名称
        //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
        Class<? extends Job> jobClass = getFullJobClassName(quartzBean);
        if (Objects.isNull(jobClass)) {
            log.warn("任务实体类全限定类名为空！");
            return false;
        }
        try {
            System.out.println(quartzBean);
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName()).setJobData(quartzBean.getJobDataMap()).build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 根据任务名称暂停定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    @Override
    public Boolean pauseScheduleJob(String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 根据任务名称恢复定时任务
     *
     * @param jobName  定时任务名
     * @param jobGroup 任务组（没有分组传值null）
     */
    @Override
    public Boolean resumeScheduleJob(String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 根据任务名称立即运行一次定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    @Override
    public Boolean runOnce(String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        try {
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 更新定时任务Simple
     *
     * @param quartzBean 定时任务信息类
     */
    @Override
    public void updateScheduleJobSimple(QuartzBean quartzBean) {
        //获取到对应任务的触发器
        String jobName = quartzBean.getJobName();
        String jobGroup = quartzBean.getJobGroup();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        // 设置定时任务执行方式
        SimpleScheduleBuilder simpleScheduleBuilder;
        if (quartzBean.getInterval() == null) {
            //单次
            simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        } else {
            //循环
            simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(quartzBean.getInterval());
        }
        // 构建触发器trigger
        Trigger trigger = null;
        if (quartzBean.getInterval() == null) {
            //单次
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null)
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(quartzBean.getStartTime())
                    .build();
        } else {
            //循环
            TriggerBuilder.newTrigger()
                    .withIdentity(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null)
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(quartzBean.getStartTime())
                    .endAt(quartzBean.getEndTime())
                    .build();
        }
        //重置对应的job
        try {
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 更新定时任务Cron
     *
     * @param quartzBean 定时任务信息类
     */
    @Override
    public Boolean updateScheduleJobCron(QuartzBean quartzBean) {
        try {
            //获取到对应任务的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    @Override
    public Boolean deleteScheduleJob(String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 获取任务状态
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return BLOCKED-阻塞;
     * COMPLETE-完成;
     * ERROR-出错;
     * NONE-不存在;
     * NORMAL-正常;
     * PAUSED-暂停;
     */
    @Override
    public String getScheduleJobStatus(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            String name = state.name();
            switch (name) {
                case "BLOCKED":
                    return "阻塞";
                case "COMPLETE":
                    return "完成";
                case "ERROR":
                    return "出错";
                case "NONE":
                    return "不存在";
                case "NORMAL":
                    return "正常";
                case "PAUSED":
                    return "暂停";
                default:
                    return name;
            }
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    /**
     * 根据定时任务名称来判断任务是否存在
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    @Override
    public Boolean checkExistsScheduleJob(String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 根据任务組刪除定時任务
     *
     * @param jobGroup 任务组
     */
    @Override
    public Boolean deleteGroupJob(String jobGroup) {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            List<JobKey> jobKeyList = new ArrayList<>(jobKeySet);
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 根据任务組批量刪除定時任务
     *
     * @param jobKeyList jobKey集合
     * @return true/false
     */
    @Override
    public Boolean batchDeleteGroupJob(List<JobKey> jobKeyList) {
        try {
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 根据任务組批量查询出jobKey
     *
     * @param jobGroup   任务组
     * @param jobKeyList jobKey集合
     * @param jobGroup   任务组
     */
    @Override
    public void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup) {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            jobKeyList.addAll(jobKeySet);
        } catch (SchedulerException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 获取job任务类全限定类名
     *
     * @param quartzBean job任务类全限定类名
     * @return QuartzJobBean(Job的子类)
     */
    public Class<? extends QuartzJobBean> getFullJobClassName(QuartzBean quartzBean) {
        String quartzBeanJobClass = quartzBean.getJobClass();
        if (StringUtils.isBlank(quartzBeanJobClass)) {
            log.warn("任务执行类(全限定类名)不能为空，你必须定义一个类作为Quartz定时任务的业务类！");
            return null;
        }
        try {
            Class<?> jobClass = Class.forName(quartzBeanJobClass);
            return (Class<? extends QuartzJobBean>) jobClass;
        } catch (ClassNotFoundException e) {
            log.error("找不到类异常：{}, 定时任务的业务类全限定类名为空或不合法", e.getLocalizedMessage(), e);
            return null;
        }
    }
}
