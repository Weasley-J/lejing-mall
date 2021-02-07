package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sku销售属性&值
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValue implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * sku_id
	 */
    private Long skuId;

	/**
	 * attr_id
	 */
    private Long attrId;

	/**
	 * 销售属性名
	 */
    private String attrName;

	/**
	 * 销售属性值
	 */
    private String attrValue;

	/**
	 * 顺序
	 */
    private Integer attrSort;

}
