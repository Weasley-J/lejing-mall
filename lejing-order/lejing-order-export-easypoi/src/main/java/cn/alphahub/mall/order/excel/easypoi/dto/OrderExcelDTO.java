package cn.alphahub.mall.order.excel.easypoi.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 订单 - easy poi dto
 *
 * @author Weasley J
 * @date 2021年7月9日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "订单id", orderNum = "0")
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * member_id
     */
    @Excel(name = "会员id", orderNum = "1")
    @JsonSerialize(using = IdSerializer.class)
    private Long memberId;

    /**
     * 订单号
     */
    @Excel(name = "订单号", orderNum = "2")
    private String orderSn;

    /**
     * 使用的优惠券
     */
    @Excel(name = "使用的优惠券", orderNum = "3")
    @JsonSerialize(using = IdSerializer.class)
    private Long couponId;

    /**
     * create_time
     */
    @Excel(name = "创建时间", orderNum = "4", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户名
     */
    @Excel(name = "用户名", orderNum = "5")
    private String memberUsername;

    /**
     * 订单总额
     */
    @Excel(name = "订单总额", orderNum = "6", numFormat = "0.00")
    private BigDecimal totalAmount;

    /**
     * 应付总额
     */
    @Excel(name = "应付总额", orderNum = "7", numFormat = "0.00")
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    @Excel(name = "运费金额", orderNum = "8", numFormat = "0.00")
    private BigDecimal freightAmount;

    /**
     * 折扣比例
     */
    @Excel(name = "折扣比例", orderNum = "9")
    private BigDecimal discountRate = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

    /**
     * 促销优惠金额（促销价、满减、阶梯价）
     */
    @Excel(name = "促销优惠金额", orderNum = "10", numFormat = "0.00")
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     */
    @Excel(name = "积分抵扣金额", orderNum = "12", numFormat = "0.00")
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    @Excel(name = "优惠券抵扣金额", orderNum = "13", numFormat = "0.00")
    private BigDecimal couponAmount;

    /**
     * 后台调整订单使用的折扣金额
     */
    @Excel(name = "后台调整订单使用的折扣金额", orderNum = "14", numFormat = "0.00")
    private BigDecimal discountAmount;

    /**
     * 支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】
     */
    @Excel(name = "支付方式", orderNum = "15", replace = {"支付宝_1", "微信_2", "银联_3", "货到付款_4", "_null"})
    private Integer payType;

    /**
     * 订单来源[0->PC订单；1->app订单]
     */
    @Excel(name = "订单来源", orderNum = "16")
    private Integer sourceType;

    /**
     * 订单状态: 0 待付款, 1 已付款, 2 已发货, 3 已完成, 4 已取消, 5 售后中, 6 售后完成
     */
    @Excel(name = "订单状态", orderNum = "17", replace = {"待付款_0", "已付款_1", "已发货_2", "已完成_3", "已取消_4", "售后中_5", "售后完成_6", "_null"})
    private Integer status;

    /**
     * 物流公司(配送方式)
     */
    @Excel(name = "物流公司", orderNum = "18")
    private String deliveryCompany;

    /**
     * 物流单号
     */
    @Excel(name = "物流单号", orderNum = "19")
    private String deliverySn;

    /**
     * 自动确认时间（天）
     */
    @Excel(name = "自动确认时间/天", orderNum = "20")
    private Integer autoConfirmDay;

    /**
     * 可以获得的积分
     */
    @Excel(name = "积分", orderNum = "21")
    private Integer integration;

    /**
     * 可以获得的成长值
     */
    @Excel(name = "成长值", orderNum = "22")
    private Integer growth;

    /**
     * 发票类型[0->不开发票；1->电子发票；2->纸质发票]
     */
    @Excel(name = "发票类型", orderNum = "23", replace = {"电子发票_1", "纸质发票_3", "_null"})
    private Integer billType;

    /**
     * 发票抬头
     */
    @Excel(name = "发票抬头", orderNum = "24")
    private String billHeader;

    /**
     * 发票内容
     */
    @Excel(name = "发票内容", orderNum = "25")
    private String billContent;

    /**
     * 收票人电话
     */
    @Excel(name = "收票人电话", orderNum = "26")
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    @Excel(name = "收票人邮箱", orderNum = "27")
    private String billReceiverEmail;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名", orderNum = "28")
    private String receiverName;

    /**
     * 收货人电话
     */
    @Excel(name = "收货人电话", orderNum = "29")
    private String receiverPhone;

    /**
     * 收货人邮编
     */
    @Excel(name = "收货人邮编", orderNum = "30")
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    @Excel(name = "省份/直辖市", orderNum = "31")
    private String receiverProvince;

    /**
     * 城市
     */
    @Excel(name = "城市", orderNum = "32")
    private String receiverCity;

    /**
     * 区
     */
    @Excel(name = "区", orderNum = "33")
    private String receiverRegion;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址", orderNum = "34")
    private String receiverDetailAddress;

    /**
     * 订单备注
     */
    @Excel(name = "订单备注", orderNum = "35")
    private String note;

    /**
     * 确认收货状态[0->未确认；1->已确认]
     */
    @Excel(name = "确认收货状态", orderNum = "36", replace = {"未确认_0", "已确认_1", "_null"})
    private Integer confirmStatus;

    /**
     * 删除状态【0->未删除；1->已删除】
     */
    @Excel(name = "删除状态", orderNum = "37", replace = {"未删除_0", "已删除_1", "_null"})
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     */
    @Excel(name = "下单时使用的积分", orderNum = "38")
    private Integer useIntegration;

    /**
     * 支付时间
     */
    @Excel(name = "支付时间", orderNum = "39", format = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    /**
     * 发货时间
     */
    @Excel(name = "发货时间", orderNum = "40", format = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    @Excel(name = "确认收货时间", orderNum = "41", format = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 评价时间
     */
    @Excel(name = "评价时间", orderNum = "42", format = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间", orderNum = "43", format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}
