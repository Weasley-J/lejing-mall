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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单退款表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_order_reimburse")
public class OrderReimburse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long reimburseId;

    /**
     * 主订单id（关联eb_order_master主键）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long masterOrderId;

    /**
     * 用户id
     */
    @JsonSerialize(using = IdSerializer.class)
    private String userId;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 商品名称
     */
    private String productTitle;

    /**
     * 退款金额（实际付款金额，单位：分）
     */
    private BigDecimal actuallyPrice;

    /**
     * 收款账户（从哪付款就退到哪里）
     */
    private String receiveAccount;

    /**
     * 退款状态（0：等待退款，1：平台处理中，2：退款成功，3：退款失败，默认0等待退款）
     */
    private Integer reimburseStatus;

    /**
     * 申请退款日期
     */
    private Date reimburseApplyDate;

    /**
     * 平台处理日期
     */
    private Date merchantProcessDate;

    /**
     * 退款成功日期
     */
    private Date refundSuccessfulDate;

    /**
     * 退款流水号
     */
    private String reimburseSerialCode;

    /**
     * 第三方交易流水号
     */
    private String thirdOutTradeCode;

    /**
     * 电商支付流水号
     */
    private String ebTransactionCode;

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
