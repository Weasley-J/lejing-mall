package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>商品sku属性组-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuItemAttrGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 属性名
     */
    private String groupName;

    /**
     * 属性集合
     */
    private List<Attr> attrs;
}
