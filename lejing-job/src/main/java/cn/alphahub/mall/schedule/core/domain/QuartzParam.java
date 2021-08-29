package cn.alphahub.mall.schedule.core.domain;

import cn.alphahub.mall.schedule.constant.ScheduleConstant;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * quartz调度任务参数对象
 *
 * @author lwj
 * @version 1.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QuartzParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 定时任务名称
     *
     * @required
     */
    @NotBlank(message = "任务名称不能为空")
    private String jobName;
    /**
     * 定时任务所在组名（没有分组传值null）
     */
    private String jobGroup;
    /**
     * 任务执行类(全限定类名)
     * <p>与springboot整合: Job class must abstract class QuartzJobBean,该类已经实现了Job class
     * Job class must implement the Job interface.
     *
     * @required
     */
    @NotBlank(message = "任务执行类的全限定类名不能为空")
    private String jobClass;
    /**
     * 自定义参数
     * <p>
     * JobDataMap instances can also be stored with a
     * Trigger.  This can be useful in the case where you have a QuartzJob
     * that is stored in the scheduler for regular/repeated use by multiple
     * Triggers, yet with each independent triggering, you want to supply the
     * QuartzJob with different data inputs.
     * </p>
     */
    private JobDataMap jobDataMap;
    /**
     * 任务描述（human-meaningful）
     */
    private String jobDescription;
    /**
     * 任务运行时间表达式（cron时间表达式）
     *
     * @required
     */
    @NotBlank(message = "cron执行表达式不能为空")
    private String cronExpression;
    /**
     * 计划执行错误策略（失火策略: 1  立即执行;  2 执行一次; 3 放弃执行，默认：2）
     */
    private Integer misfirePolicy;
    /**
     * 任务状态( 1 正常, 0 暂停)
     */
    private Integer status;
    /**
     * 任务状态名称
     */
    private String statusName;
    /**
     * 任务开始时间
     * <p>用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束</p>
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 任务循环间隔-单位：分钟
     * <p>用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束</p>
     */
    private Integer interval;
    /**
     * 任务结束时间
     * <p>用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束</p>
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 获取存Job实例的状态信息，包括参数
     *
     * @param jobParams 任务参数，
     * @return JobDataMap
     */
    public static JobDataMap getJobDataMap(String jobParams) {
        JobDataMap dataMap = new JobDataMap();
        if (StringUtils.isNotBlank(jobParams)) {
            dataMap.put(ScheduleConstant.JOB_DATA_PARAM_KEY, jobParams);
        }
        return dataMap;
    }

    public String getStatusName() {
        return Objects.isNull(status) ? "" : ScheduleConstant.JobStatusEnum.getName(status);
    }

    public JobDataMap getJobDataMap() {
        return CollectionUtils.isEmpty(jobDataMap) ? new JobDataMap() : jobDataMap;
    }

    public Date getStartTime() {
        return Objects.nonNull(startTime) ? startTime : new Date();
    }
}
