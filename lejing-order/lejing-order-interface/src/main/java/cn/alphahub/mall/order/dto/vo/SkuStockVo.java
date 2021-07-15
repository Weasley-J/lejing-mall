package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sku库存
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuStockVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 是否有库存
     */
    private Boolean hasStock;
}
