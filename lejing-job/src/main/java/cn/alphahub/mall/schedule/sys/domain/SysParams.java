package cn.alphahub.mall.schedule.sys.domain;

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
 * 参数管理对象 sys_params
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:21:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_params")
public class SysParams implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 类型，是否系统参数；1：系统参数；0：非系统参数。
     */
    private Integer paramType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updater;

    /**
     * 删除状态 ；0：正常，1：删除
     */
    private Integer delFlag;

    /**
     * 删除者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deleter;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deleteDate;

}
