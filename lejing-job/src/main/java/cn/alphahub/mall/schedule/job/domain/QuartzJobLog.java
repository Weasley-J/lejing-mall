package cn.alphahub.mall.schedule.job.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz定时任务调度日志对象 quartz_job_log
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("quartz_job_log")
public class QuartzJobLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * quartz任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /**
     * 任务名称
     */
    @TableField(condition = SqlCondition.LIKE)
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务描述（human-meaningful）
     */
    private String jobDescription;

    /**
     * 任务执行类的全限定类名
     */
    private String jobClass;

    /**
     * 执行状态: 1 正常, 0 失败
     */
    private Integer status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
