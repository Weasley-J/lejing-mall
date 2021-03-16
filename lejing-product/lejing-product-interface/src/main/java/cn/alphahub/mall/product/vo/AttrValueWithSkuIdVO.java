package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b>商品属性值skuId列表-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttrValueWithSkuIdVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 商品skuId集合
     */
    private String skuIds;
}
