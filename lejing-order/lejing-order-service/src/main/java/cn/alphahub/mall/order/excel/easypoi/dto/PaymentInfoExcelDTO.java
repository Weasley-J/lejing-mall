package cn.alphahub.mall.order.excel.easypoi.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付信息表 - easy poi dto
 *
 * @author Weasley J
 * @date 2021年7月8日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Excel(name = "id")
    private Long id;

    /**
     * 订单号（对外业务号）
     */
    @Excel(name = "订单号(对外业务号)")
    private String orderSn;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 支付宝交易流水号
     */
    @Excel(name = "支付宝交易流水号")
    private String alipayTradeNo;

    /**
     * 支付总金额
     */
    @Excel(name = "支付总金额")
    private BigDecimal totalAmount;

    /**
     * 交易内容
     */
    @Excel(name = "交易内容")
    private String subject;

    /**
     * 支付状态
     */
    @Excel(name = "支付状态")
    private String paymentStatus;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 确认时间
     */
    @Excel(name = "确认时间")
    private Date confirmTime;

    /**
     * 回调内容
     */
    @Excel(name = "回调内容")
    private String callbackContent;

    /**
     * 回调时间
     */
    @Excel(name = "回调时间")
    private Date callbackTime;

}
