package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>商品sku</p>
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skus implements Serializable {

    /**
     * 属性集合
     */
    private List<Attr> attr;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品标题
     */
    private String skuTitle;

    /**
     * 商品副标题
     */
    private String skuSubtitle;

    /**
     * 商品图片集合
     */
    private List<Images> images;

    /**
     * 描述
     */
    private List<String> descar;

    /**
     * 总数
     */
    private int fullCount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     *
     */
    private int countStatus;

    /**
     * 总价
     */
    private BigDecimal fullPrice;

    /**
     * 减价
     */
    private BigDecimal reducePrice;

    /**
     * 价格状态
     */
    private int priceStatus;

    /**
     * 会员价
     */
    private List<MemberPrice> memberPrice;
}
