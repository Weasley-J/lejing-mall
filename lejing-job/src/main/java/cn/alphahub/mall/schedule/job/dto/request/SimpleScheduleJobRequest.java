package cn.alphahub.mall.schedule.job.dto.request;

import cn.alphahub.common.valid.group.Edit;
import cn.alphahub.common.valid.group.Insert;
import cn.alphahub.common.valid.group.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 简单的调度任务 - 请求参数
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SimpleScheduleJobRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 定时任务名称
     *
     * @required
     */
    @NotBlank(message = "任务名称不能为空", groups = {Insert.class, Edit.class, Query.class})
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
    @NotBlank(message = "任务执行类的全限定类名不能为空", groups = {Insert.class})
    private String jobClass;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     * 任务状态：1 正常, 0 暂停，默认：1
     */
    private Integer status = 1;
    /**
     * 任务开始时间(yyyy-MM-dd HH:mm:ss)
     * <p>用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束</p>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 任务循环间隔-单位：分钟
     * <p>
     * 用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束
     *     <ul>
     *         <li>{@code interval}为null，单次触发</li>
     *         <li>{@code interval}不为null，循环触发，从什么时间开始，循环间隔多少分钟，什么时间结束</li>
     *     </ul>
     * </p>
     */
    private Integer interval;
    /**
     * 任务结束时间(yyyy-MM-dd HH:mm:ss)
     * <p>用于创建简单的调度任务（simpleScheduleBuilder）：从什么时间开始，循环间隔多少分钟，什么时间结束</p>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
