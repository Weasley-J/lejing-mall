package cn.alphahub.mall.reserve.order.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单库存表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_order_stock")
public class OrderStock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 库存id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long stockId;

    /**
     * 场次id（表相关类目主键id）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long itemId;

    /**
     * 主订单id
     */
    private Long masterOrderId;

    /**
     * 采购数量（单位：场）
     */
    private Integer purchaseQuantity;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注信息
     */
    private String remark;

}
