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
 * quartz定时任务调度对象 quartz_job
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
@TableName("quartz_job")
public class QuartzJob implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 任务执行类的全限定类名
     */
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
     */
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

}
