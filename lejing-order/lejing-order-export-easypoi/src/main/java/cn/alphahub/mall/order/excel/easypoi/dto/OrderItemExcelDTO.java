package cn.alphahub.mall.order.excel.easypoi.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项信息 - Excel DTO
 *
 * @author Weasley J
 * @date 2021年7月8日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItemExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", orderNum = "0")
    private Long id;

    /**
     * order_id
     */
    @Excel(name = "订单id", orderNum = "1")
    private String orderId;

    /**
     * order_sn
     */
    @Excel(name = "订单编号", orderNum = "2")
    private String orderSn;

    /**
     * spu_id
     */
    @Excel(name = "spu_id", orderNum = "3")
    private String spuId;

    /**
     * spu_name
     */
    @Excel(name = "商品名称", orderNum = "4")
    private String spuName;

    /**
     * spu_pic
     */
    @Excel(name = "商品图片", orderNum = "5")
    private String spuPic;

    /**
     * 品牌
     */
    @Excel(name = "商品品牌", orderNum = "6")
    private String spuBrand;

    /**
     * 商品分类id
     */
    @Excel(name = "商品分类id", orderNum = "7")
    private String categoryId;

    /**
     * 商品sku编号
     */
    @Excel(name = "商品sku编号", orderNum = "8")
    private String skuId;

    /**
     * 商品sku名字
     */
    @Excel(name = "商品sku名字", orderNum = "9")
    private String skuName;

    /**
     * 商品sku图片
     */
    @Excel(name = "商品sku图片", orderNum = "10")
    private String skuPic;

    /**
     * 商品sku价格
     */
    @Excel(name = "商品sku价格", orderNum = "11")
    private BigDecimal skuPrice;

    /**
     * 商品购买的数量
     */
    @Excel(name = "商品购买的数量", orderNum = "12")
    private Integer skuQuantity;

    /**
     * 商品销售属性组合（JSON）
     */
    @Excel(name = "商品销售属性组合", orderNum = "13")
    private String skuAttrsVals;

    /**
     * 商品促销分解金额
     */
    @Excel(name = "商品促销分解金额", orderNum = "14", numFormat = "0.00")
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    @Excel(name = "优惠券优惠分解金额", orderNum = "15", numFormat = "0.00")
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    @Excel(name = "积分优惠分解金额", orderNum = "16", numFormat = "0.00")
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    @Excel(name = "该商品经过优惠后的分解金额", orderNum = "17")
    private BigDecimal realAmount;

    /**
     * 赠送积分
     */
    @Excel(name = "赠送积分", orderNum = "18")
    private Integer giftIntegration;

    /**
     * 赠送成长值
     */
    @Excel(name = "赠送成长值", orderNum = "19")
    private Integer giftGrowth;

    /**
     * 导出日期
     */
    @Excel(name = "导出日期", orderNum = "20", format = "yyyy-MM-dd HH:mm:ss")
    private Date exportTime = new Date();
}
