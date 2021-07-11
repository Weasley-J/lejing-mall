package cn.alphahub.mall.order.excel.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OrderItemExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "序号", index = 0)
    private Long id;

    /**
     * order_id
     */
    @ExcelProperty(value = "订单id", index = 1)
    private String orderId;

    /**
     * order_sn
     */
    @ExcelProperty(value = "订单编号", index = 2)
    private String orderSn;

    /**
     * spu_id
     */
    @ExcelProperty(value = "spu_id", index = 3)
    private String spuId;

    /**
     * spu_name
     */
    @ExcelProperty(value = "商品名称", index = 4)
    private String spuName;

    /**
     * spu_pic
     */
    @ExcelProperty(value = "商品图片", index = 5)
    private String spuPic;

    /**
     * 品牌
     */
    @ExcelProperty(value = "商品品牌", index = 6)
    private String spuBrand;

    /**
     * 商品分类id
     */
    @ExcelProperty(value = "商品分类id", index = 7)
    private String categoryId;

    /**
     * 商品sku编号
     */
    @ExcelProperty(value = "商品sku编号", index = 8)
    private String skuId;

    /**
     * 商品sku名字
     */
    @ExcelProperty(value = "商品sku名字", index = 9)
    private String skuName;

    /**
     * 商品sku图片
     */
    @ExcelProperty(value = "商品sku图片", index = 10)
    private String skuPic;

    /**
     * 商品sku价格
     */
    @ExcelProperty(value = "商品sku价格", index = 11)
    private BigDecimal skuPrice;

    /**
     * 商品购买的数量
     */
    @ExcelProperty(value = "商品购买的数量", index = 12)
    private Integer skuQuantity;

    /**
     * 商品销售属性组合（JSON）
     */
    @ExcelProperty(value = "商品销售属性组合", index = 13)
    private String skuAttrsVals;

    /**
     * 商品促销分解金额
     */
    @ExcelProperty(value = "商品促销分解金额", index = 14)
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    @ExcelProperty(value = "优惠券优惠分解金额", index = 15)
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    @ExcelProperty(value = "积分优惠分解金额", index = 16)
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    @ExcelProperty(value = "该商品经过优惠后的分解金额", index = 17)
    private BigDecimal realAmount;

    /**
     * 赠送积分
     */
    @ExcelProperty(value = "赠送积分", index = 18)
    private Integer giftIntegration;

    /**
     * 赠送成长值
     */
    @ExcelProperty(value = "赠送成长值", index = 19)
    private Integer giftGrowth;

    /**
     * 导出日期
     */
    @ExcelProperty(value = "导出日期", index = 20)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date exportTime = new Date();
}
