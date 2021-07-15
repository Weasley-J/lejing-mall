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
 * 主订单表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_order_master")
public class OrderMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主订单id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long masterOrderId;

    /**
     * 主订单号
     */
    private String masterOrderCode;

    /**
     * 下单人ID
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long userId;

    /**
     * 下单人手机号
     */
    private String userPhone;

    /**
     * 下单人标签
     */
    private String userLabel;

    /**
     * 用户所属社区
     */
    private String userCommunity;

    /**
     * 订单类型：COMMON-普通订单，PURCHASE-团购订单
     */
    private String orderType;

    /**
     * 订单状态：PENDING_PAYMENT待付款，PENDING_STOCK待采购，TO_BE_DELIVERED待发货，SHIPPED已发货，DEAL_DONE已成交，CLOSED已关闭，CANCELLED已取消
     */
    private String orderStatus;

    /**
     * 支付方式：WX_PAY-微信，ALI_PAY-支付宝，OTHER_PAY-其他支付方式，默认：OTHER_PAY
     */
    private String payType;

    /**
     * 订单总金额(单位：分)
     */
    private BigDecimal totalPrice;

    /**
     * 实际支付金额（单位：分）
     */
    private BigDecimal actuallyPrice;

    /**
     * 订单总运费（单位：分）
     */
    private BigDecimal postagePrice;

    /**
     * 订单总优惠金额（单位：分）
     */
    private BigDecimal discountPrice;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 成交时间
     */
    private Date dealTime;

    /**
     * 状态( EFFECTIVE-有效；INVALID-无效， 默认-EFFECTIVE )
     */
    private String status;

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
