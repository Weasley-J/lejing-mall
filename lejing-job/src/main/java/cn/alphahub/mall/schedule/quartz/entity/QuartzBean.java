package cn.alphahub.mall.schedule.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.quartz.JobDataMap;

import java.io.Serializable;
import java.util.Date;

/**
 * Quartz参数实体类
 *
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QuartzBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务id
     */
    private Long id;

    /**
     * 任务名称（自定义）
     */
    private String jobName;

    /**
     * 任务组（自定义）
     */
    private String jobGroup;

    /**
     * 任务执行类(全限定类名)
     * <b>与springboot整合: Job class must abstract class QuartzJobBean,该类已经实现了Job class</b>
     * Job class must implement the Job interface.
     */
    private String jobClass;

    /**
     * 任务状态,启动还是暂停; 1:正常,0:暂停
     */
    private Integer status;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务循环间隔-单位：分钟
     */
    private Integer interval;

    /**
     * 任务结束时间
     */
    private Date endTime;

    /**
     * 任务运行时间表达式（cron时间表达式）
     */
    private String cronExpression;

    /**
     * 自定义参数
     * JobDataMap instances can also be stored with a
     * Trigger.  This can be useful in the case where you have a QuartzJob
     * that is stored in the scheduler for regular/repeated use by multiple
     * Triggers, yet with each independent triggering, you want to supply the
     * QuartzJob with different data inputs.
     */
    private JobDataMap jobDataMap;
}
