package cn.alphahub.mall.schedule.core.service;

import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;

import java.util.List;
import java.util.Objects;

import static cn.alphahub.mall.schedule.constant.ScheduleConstant.MISFIRE_HANDLING_DO_NOTHING;
import static cn.alphahub.mall.schedule.constant.ScheduleConstant.MISFIRE_HANDLING_FIRE_AND_PROCEED;
import static cn.alphahub.mall.schedule.constant.ScheduleConstant.MISFIRE_HANDLING_IGNORE_MISFIRES;

/**
 * quartz任务调度上层接口
 *
 * @author lwj
 * @version 1.0.1
 */
public interface QuartzCoreService {
    /**
     * 任务名称前缀
     */
    String JOB_NAME_PREFIX = "SCHEDULE_TASK_";

    /**
     * 创建Simple定时任务
     * <p> param.getInterval()==null表示单次提醒，否则循环提醒（param.getEndTime()!=null）</p>
     *
     * @param param Quartz参数实体类
     * @return true：成功，false：失败
     */
    boolean createSimpleScheduleJob(QuartzParam param);

    /**
     * 创建Cron定时任务
     * 定时任务创建之后默认启动状态
     *
     * @param param 定时任务信息类
     * @return true：成功，false：失败
     */
    boolean createCronScheduleJob(QuartzParam param);

    /**
     * 暂停定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean pauseScheduleJob(String jobName, String jobGroup);

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  定时任务名
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean resumeScheduleJob(String jobName, String jobGroup);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     */
    void updateSimpleScheduleJob(QuartzParam param);

    /**
     * 更新Cron定时任务
     *
     * @param param 定时任务信息类
     * @return true：成功，false：失败
     */
    boolean updateCronScheduleJob(QuartzParam param);

    /**
     * 从调度器当中删除定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败@return true：成功，false：失败
     */
    boolean deleteScheduleJob(String jobName, String jobGroup);

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
    String getScheduleJobStatus(String jobName, String jobGroup);

    /**
     * 判断任务是否存在
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean checkExistsScheduleJob(String jobName, String jobGroup);

    /**
     * 根据任务組刪除定時任务
     *
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    boolean deleteGroupJob(String jobGroup);

    /**
     * 根据任务組批量刪除定時任务
     *
     * @param jobKeyList job key list
     * @return true：成功，false：失败
     */
    boolean batchDeleteGroupJob(List<JobKey> jobKeyList);

    /**
     * 根据任务組批量查询出jobKey
     *
     * @param jobGroup   任务组
     * @param jobKeyList job key list
     */
    void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup);

    /**
     * 立即运行一次定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean executeAtNow(String jobName, String jobGroup);

    /**
     * 获取任务执行类Class
     *
     * @param targetClass 任务执行类全限定类名
     * @return 任务执行类Class
     */
    Class<? extends Job> getJobClass(String targetClass);

    /**
     * 处理失火策略
     *
     * @param cronScheduleBuilder CronScheduleBuilder是一个ScheduleBuilder,它为Trigger定义基于CronExpression的计划
     * @param misfirePolicy       计划执行错误策略（失火策略: 1 立即执行;  2 执行一次; 3 放弃执行）
     * @return CronScheduleBuilder
     */
    default CronScheduleBuilder handlingCronScheduleMisfirePolicy(CronScheduleBuilder cronScheduleBuilder, Integer misfirePolicy) {
        if (Objects.isNull(misfirePolicy)) {
            return cronScheduleBuilder;
        }
        switch (misfirePolicy) {
            case MISFIRE_HANDLING_IGNORE_MISFIRES:
                return cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            case MISFIRE_HANDLING_FIRE_AND_PROCEED:
                return cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            case MISFIRE_HANDLING_DO_NOTHING:
                return cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
            default:
                return cronScheduleBuilder;
        }
    }

    /**
     * 防止创建时存在数据问题, 先移除调度任务，然后在执行创建操作
     *
     * @param scheduler Scheduler维护JobDetail和Trigger的注册表，一旦注册，Scheduler负责在Job相关的Trigger时（当它们的预定时间到达时）执行Job
     * @param param     quartz调度任务参数
     */
    @SneakyThrows
    default void deleteJobIfExists(Scheduler scheduler, QuartzParam param) {
        JobKey jobKey = getJobKey(param);
        boolean exists = scheduler.checkExists(jobKey);
        if (exists) {
            scheduler.deleteJob(jobKey);
        }
    }

    /**
     * 处理任务状态
     * {@link QuartzParam#status}
     *
     * @param scheduler Quartz Scheduler
     * @param param     quartz参数实体类
     */
    @SneakyThrows
    default void handlingJobStatus(Scheduler scheduler, QuartzParam param) {
        if (Objects.equals(param.getStatus(), 0)) {
            JobKey jobKey = getJobKey(param);
            scheduler.pauseJob(jobKey);
        }
    }

    /**
     * 获取JobKey
     *
     * @param param quartz调度任务参数
     * @return JobKey
     */
    default JobKey getJobKey(QuartzParam param) {
        JobKey jobKey = null;
        if (StringUtils.isNotBlank(param.getJobName())) {
            jobKey = JobKey.jobKey(param.getJobName());
        }
        if (StringUtils.isNoneBlank(param.getJobName(), param.getJobGroup())) {
            jobKey = JobKey.jobKey(param.getJobName(), param.getJobGroup());
        }
        return jobKey;
    }

    /**
     * 获取TriggerKey
     *
     * @param param quartz调度任务参数
     * @return TriggerKey
     */
    default TriggerKey getTriggerKey(QuartzParam param) {
        return TriggerKey.triggerKey(param.getJobName(), param.getJobGroup());
    }

}
