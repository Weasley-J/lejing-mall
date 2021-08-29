package cn.alphahub.mall.schedule.sys.domain;

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
 * 字典类型对象 sys_dict_type
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-28 22:03:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_dict_type")
public class SysDictType implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 字典名称
     */
    @TableField(condition = SqlCondition.LIKE)
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态: 0 正常，1 删除
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updater;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deleteTime;

    /**
     * 删除者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deleter;

}
