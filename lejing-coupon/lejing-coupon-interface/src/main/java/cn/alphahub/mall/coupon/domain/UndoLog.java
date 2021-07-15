package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * AT transaction mode undo table对象 undo_log
 *
 * @author Weasley J
 * @date 2021-06-12 03:06:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("undo_log")
public class UndoLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * branch transaction id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long branchId;

    /**
     * global transaction id
     */
    private String xid;

    /**
     * undo_log context,such as serialization
     */
    private String context;

    /**
     * rollback info
     */
    private String rollbackInfo;

    /**
     * 0:normal status,1:defense status
     */
    private Integer logStatus;

    /**
     * create datetime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logCreated;

    /**
     * modify datetime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logModified;

}
