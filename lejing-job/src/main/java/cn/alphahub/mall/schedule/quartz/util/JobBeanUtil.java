package cn.alphahub.mall.schedule.quartz.util;


import cn.alphahub.mall.schedule.job.domain.SysJob;
import cn.alphahub.mall.schedule.quartz.entity.QuartzBean;
import org.quartz.JobDataMap;

/**
 * 将持久层SysJob定时任务参数实体转换为Quartz参数实体
 * <p>
 * <b>自定义参数:</b>
 * JobDataMap instances can also be stored with a
 * Trigger.  This can be useful in the case where you have a QuartzJob
 * that is stored in the scheduler for regular/repeated use by multiple
 * Triggers, yet with each independent triggering, you want to supply the
 * QuartzJob with different data inputs.
 * </p>
 *
 * @author liuwenjing
 */
public class JobBeanUtil {

    private JobBeanUtil() {
    }

    /**
     * SysJob -> QuartzBean
     *
     * @param sysJob SysJob
     * @return QuartzBean
     */
    public static QuartzBean getQuartzBean(SysJob sysJob) {
        return QuartzBean.builder()
                .id(sysJob.getJobId())
                .jobName(sysJob.getJobName())
                .jobGroup(sysJob.getJobGroup())
                .jobClass(sysJob.getInvokeTarget())
                .cronExpression(sysJob.getCronExpression())
                .status(sysJob.getStatus())
                .jobDataMap(new JobDataMap())
                .build();
    }
}
