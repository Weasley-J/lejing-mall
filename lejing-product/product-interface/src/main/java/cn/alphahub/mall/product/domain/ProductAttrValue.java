package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * spu属性值
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_product_attr_value")
public class ProductAttrValue implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 商品id
	 */
    private Long spuId;

	/**
	 * 属性id
	 */
    private Long attrId;

	/**
	 * 属性名
	 */
    private String attrName;

	/**
	 * 属性值
	 */
    private String attrValue;

	/**
	 * 顺序
	 */
    private Integer attrSort;

	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】
	 */
    private Integer quickShow;

}