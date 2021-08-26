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
 * 定时任务调度日志表对象 sys_job_log
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:15:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_job_log")
public class SysJobLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 任务日志ID
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
     * 日志信息
     */
    private String jobMessage;

    /**
     * 调用目标字符串，任务执行类的全限定类名
     */
    private String invokeTarget;

    /**
     * 执行状态（1:正常,0:失败）
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
