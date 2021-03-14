package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品id名称-视图对象
 *
 * @author Weasley J
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandVO implements Serializable {

    /**
     * 品牌id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;
}
