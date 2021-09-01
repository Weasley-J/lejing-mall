package cn.alphahub.mall.schedule.job.dto;

import cn.alphahub.common.valid.annotation.Cron;
import cn.alphahub.common.valid.group.EditGroup;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.common.valid.group.QueryGroup;
import cn.alphahub.mall.schedule.constant.ScheduleConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz定时任务调度 - DTO
 *
 * @author Weasley J
 * @date 2021-08-29 15:58:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QuartzJobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "任务ID不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 任务名称
     *
     * @required
     */
    @NotBlank(message = "任务名称不能为空", groups = {InsertGroup.class, EditGroup.class, QueryGroup.class})
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务执行类的全限定类名
     *
     * @required
     */
    @NotBlank(message = "任务执行类的全限定类名不能为空", groups = {InsertGroup.class, EditGroup.class})
    private String jobClass;

    /**
     * 任务参数（和JobDataMap对应，通过约定的key取出数据）
     */
    private String jobParams;

    /**
     * 任务描述（human-meaningful）
     */
    private String jobDescription;

    /**
     * cron执行表达式
     *
     * @required
     */
    @NotBlank(message = "cron执行表达式不能为空", groups = {InsertGroup.class, EditGroup.class})
    @Cron(message = "cron表达式不合法", groups = {InsertGroup.class, EditGroup.class})
    private String cronExpression;

    /**
     * 计划执行错误策略(失火策略: 0 默认; 1  立即执行;  2 执行一次; 3 放弃执行)
     */
    private Integer misfirePolicy;

    /**
     * 是否并发执行（1：允许；0：禁止）
     */
    private Integer isConcurrent;
    /**
     * 任务状态( 1 正常, 0 暂停，默认：1)
     */
    private Integer status;
    /**
     * 任务状态名称
     */
    private String statusName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 备注信息
     */
    private String remark;

    public String getStatusName() {
        return StringUtils.isBlank(statusName) ? ScheduleConstant.JobStatusEnum.getName(status) : statusName;
    }

}
