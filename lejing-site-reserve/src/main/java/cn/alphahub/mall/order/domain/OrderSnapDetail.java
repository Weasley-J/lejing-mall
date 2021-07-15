package cn.alphahub.mall.order.domain;

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
 * 订单快照表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_order_snap_detail")
public class OrderSnapDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id，订单号
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long orderId;

    /**
     * 主订单id（关联eb_order_master主键）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long masterOrderId;

    /**
     * 用户id（当前用户id，见用户表，用户其他信息去用户表捞）
     */
    @JsonSerialize(using = IdSerializer.class)
    private String userId;

    /**
     * 下单用户姓名
     */
    private String username;

    /**
     * 预约手机号
     */
    private String userPhone;

    /**
     * 快照信息关联的产品id（全局唯一的产品，id如：场地预约的场地id等）
     */
    private String itemId;

    /**
     * item名称
     */
    private String itemTitle;

    /**
     * item副标题
     */
    private String itemSubtitle;

    /**
     * item品牌
     */
    private String itemBrand;

    /**
     * itemurl链接（图片超链接）
     */
    private String itemUrl;

    /**
     * item邮资，邮费
     */
    private BigDecimal itemPostagePrice;

    /**
     * item数量
     */
    private BigDecimal itemQuantity;

    /**
     * item数量单位
     */
    private String itemQuantityUnit;

    /**
     * 折扣金额(单位：分)
     */
    private BigDecimal itemDiscountPrice;

    /**
     * 订单单价(单位：分)
     */
    private BigDecimal itemUnitPrice;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Integer deleted;

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
