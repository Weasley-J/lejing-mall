package cn.alphahub.mall.ware.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wms_purchase")
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 采购单id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 采购人id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long assigneeId;

    /**
     * 采购人名
     */
    private String assigneeName;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 仓库id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long wareId;

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 更新日期
     */
    private Date updateTime;

}
