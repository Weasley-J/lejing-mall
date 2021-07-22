package cn.alphahub.mall.order.excel.easypoi.dto.response;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 账单明细查询 - 分页数据响应
 * <p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021年7月21日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BillingDetailQueryResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单号
     */
    @Excel(name = "订单号", width = 22, needMerge = true, orderNum = "0")
    private String orderNo;
    /**
     * 交易流水号
     */
    @Excel(name = "交易流水号", width = 22, needMerge = true, orderNum = "1")
    private String orderItemTransactionNo;
    /**
     * 收支类型：0 支出，1 收入
     * <ul>
     *     <li>支付类型：0 支付，1 退款 <-> 收支类型 0 支出，1 收入</li>
     * </ul>
     */
    @Excel(name = "收支类型", replace = {"支出_0", "收入_1", "_null"}, needMerge = true, orderNum = "2")
    private Integer incomeExpendType;
    /**
     * 状态: 0 未付款 1 已付款
     */
    @Excel(name = "状态", replace = {"未付款_0", "已付款_1", "_null"}, needMerge = true, orderNum = "3")
    private Integer settlePayStatus;
    /**
     * 交易来源：0 商城，1 疫苗
     */
    @Excel(name = "交易来源", replace = {"商城_0", "疫苗_1", "_null"}, needMerge = true, orderNum = "4")
    private Integer orderSource;
    /**
     * 商品名称
     */
    @Excel(name = "商品名称", width = 45, needMerge = true, orderNum = "5")
    private String goodsName;
    /**
     * 商品规格
     */
    @Excel(name = "商品规格", width = 30, needMerge = true, orderNum = "6")
    private String goodsSpec;
    /**
     * 商品分类: 1.大健康商品 2.OTC药品 3.医疗器械 4.处方药品
     */
    @ExcelIgnore
    @JsonIgnore
    private Integer goodsKind;
    /**
     * 商品类型:0 默认，1 全球购，2医生端权益，3消费端权益，4 疫苗
     */
    @ExcelIgnore
    @JsonIgnore
    private Integer goodsType;
    /**
     * 零售价
     */
    @Excel(name = "零售价", width = 15, needMerge = true, orderNum = "7")
    private BigDecimal retailPrice;
    /**
     * 数量
     */
    @Excel(name = "数量", needMerge = true, orderNum = "8")
    private Integer goodsNum;
    /**
     * 商品总金额 （ 商品零售价 * 商品数量 ）
     */
    @Excel(name = "商品总金额", width = 15, needMerge = true, orderNum = "9")
    private BigDecimal goodsTotalPrice;
    /**
     * 运费
     */
    @Excel(name = "运费", needMerge = true, orderNum = "10")
    private BigDecimal carriageAmount;
    /**
     * 订单金额 ( 商品零售价 * 商品数量 + 运费）
     */
    @Excel(name = "订单金额", width = 15, needMerge = true, orderNum = "11")
    private BigDecimal orderAmount;
    /**
     * 平台优惠明细
     */
    @ExcelCollection(id = "platformDiscountList", name = "平台优惠明细", type = List.class, orderNum = "12")
    private List<PlatformDiscountVo> platformDiscountList;
    /**
     * 商家优惠明细
     */
    @ExcelCollection(id = "merchantDiscountList", name = "商家优惠明细", type = List.class, orderNum = "13")
    private List<MerchantDiscountVo> merchantDiscountList;
    /**
     * 抵扣金额明细
     */
    @ExcelCollection(id = "deductAmountList", name = "抵扣金额明细", type = List.class, orderNum = "14")
    private List<DeductVo> deductAmountList;
    /**
     * 实收金额 （liquidation_trade_order_item.trade_amount 商品交易金额（实际付款分摊的金额））
     */
    @Excel(name = "实收金额", width = 13, needMerge = true, orderNum = "15")
    private BigDecimal actualAmount;
    /**
     * 佣金率
     */
    @Excel(name = "佣金率", width = 13, needMerge = true, orderNum = "16")
    private BigDecimal platformCommissionRatio;
    /**
     * 佣金
     */
    @Excel(name = "佣金", width = 13, needMerge = true, orderNum = "16")
    private BigDecimal platformCommissionAmount;
    /**
     * 结算金额
     */
    @Excel(name = "结算金额", width = 13, needMerge = true, orderNum = "18")
    private BigDecimal settleAmount;
    /**
     * 结算时间
     */
    @Excel(name = "结算时间", format = "yyyy-MM-dd HH:mm:ss", width = 22, needMerge = true, orderNum = "19")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime settleTime;
    /**
     * 结算模式 1 自营 0 代理
     */
    @Excel(name = "结算模式", replace = {"自营_1", "代理_0", "_null"}, width = 12, needMerge = true, orderNum = "20")
    private Integer settleType;
    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称", width = 13, needMerge = true, orderNum = "21")
    private String supplierName;
    /**
     * 供应商id
     */
    @Excel(name = "供应商id", width = 12, needMerge = true, orderNum = "22")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long supplierId;
    /**
     * 商品skuId
     */
    @Excel(name = "商品skuId", width = 12, needMerge = true, orderNum = "23")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;
    /**
     * 商户订单号
     */
    @Excel(name = "商户订单号", width = 35, needMerge = true, orderNum = "24")
    private String payNo;
    /**
     * 三方订单号
     */
    @Excel(name = "三方订单号", width = 35, needMerge = true, orderNum = "25")
    private String thirdTransactionNo;
    /**
     * 支付方式：ALI_PAY(支付宝支付),WECHAT_PAY（微信支付）
     */
    @Excel(name = "支付方式", width = 12, needMerge = true, orderNum = "26")
    private String payChannelCode;
    /**
     * 支付时间
     */
    @Excel(name = "支付时间", format = "yyyy-MM-dd HH:mm:ss", width = 22, needMerge = true, orderNum = "27")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paySuccessTime;
    /**
     * 是否处方药
     */
    @Excel(name = "是否处方药", width = 13, replace = {"是_true", "否_false"}, needMerge = true, orderNum = "28")
    private Boolean isPrescription;
    /**
     * 清算订单表ID
     */
    @ExcelIgnore
    @JsonIgnore
    private String liquidationTradeOrderId;
    /**
     * 清算子订单表ID
     */
    @ExcelIgnore
    @JsonIgnore
    private String liquidationTradeOrderItemId;

    public String getPayChannelCode() {
        return PayChannelEnum.getName(payChannelCode);
    }

    /**
     * 支付方式名称获取
     */
    @Getter
    @AllArgsConstructor
    public enum PayChannelEnum {
        /**
         * ALI_PAY 支付宝支付
         */
        ALI_PAY("ALI_PAY", "支付宝支付"),
        WECHAT_PAY("WECHAT_PAY", "微信支付");

        /**
         * 支付方式
         */
        private final String key;
        /**
         * 支付方名称
         */
        private final String name;

        /**
         * 获取支付方名称
         *
         * @param key 支付方式
         * @return 支付方名称
         */
        public static String getName(String key) {
            return Arrays.stream(PayChannelEnum.values())
                    .filter(original -> Objects.equals(original.getKey(), key))
                    .findFirst().map(PayChannelEnum::getName).orElse("");
        }
    }

    /**
     * 平台优惠金额明细
     */
    @Data
    @Accessors(chain = true)
    @ExcelTarget(value = "platformDiscountList")
    public static class PlatformDiscountVo implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 平台优惠类型属性id（优惠券id、促销活动id）
         */
        @Excel(name = "平台优惠类型属性id", width = 20, orderNum = "1")
        private String discountItemId;
        /**
         * 平台优惠类型名称
         */
        @Excel(name = "平台优惠类型名称", width = 20, orderNum = "0")
        private String discountTypeName;
        /**
         * 该平台优惠金额（已初始化: ￥0.00）
         */
        @Excel(name = "该平台优惠金额", width = 20, orderNum = "2")
        private BigDecimal discountPrice = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);
    }

    /**
     * 商家优惠金额明细
     */
    @Data
    @Accessors(chain = true)
    @ExcelTarget(value = "merchantDiscountList")
    public static class MerchantDiscountVo implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 商户优惠类型属性id（优惠券id、促销活动id）
         */
        @Excel(name = "商户优惠类型属性id", width = 20, orderNum = "1")
        private String discountItemId;
        /**
         * 商户优惠类型名称
         */
        @Excel(name = "商户优惠类型名称", width = 20, orderNum = "0")
        private String discountTypeName;
        /**
         * 该商户优惠金额（已初始化: ￥0.00）
         */
        @Excel(name = "该商户优惠金额", width = 20, orderNum = "3")
        private BigDecimal discountPrice = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);
    }

    /**
     * 抵扣金额明细
     */
    @Data
    @Accessors(chain = true)
    @ExcelTarget(value = "deductAmountList")
    public static class DeductVo implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 抵扣类型（邦指数、优医币）
         */
        @Excel(name = "抵扣类型", width = 15)
        private String deductType;
        /**
         * 抵扣金额（已初始化: ￥0.00）
         */
        @Excel(name = "抵扣金额", width = 15)
        private BigDecimal deductPrice = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);
    }
}
