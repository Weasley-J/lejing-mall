package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>商品详情页销售属性-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuItemSaleAttrVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * skuId销售属性值集合
     */
    private List<AttrValueWithSkuIdVO> attrValues;
}
