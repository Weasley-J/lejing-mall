package cn.alphahub.mall.ware.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b>商品库存-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareSkuVO implements Serializable {

    /**
     * sku id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long skuId;

    /**
     * 库存数 >0 有库存
     */
    private Integer stock;

    /**
     * 是否有库存
     */
    private Boolean hasStock;
}
