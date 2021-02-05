package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_sku_info")
public class SkuInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * skuId
	 */
    @TableId
    private Long skuId;

	/**
	 * spuId
	 */
    private Long spuId;

	/**
	 * sku名称
	 */
    private String skuName;

	/**
	 * sku介绍描述
	 */
    private String skuDesc;

	/**
	 * 所属分类id
	 */
    private Long catalogId;

	/**
	 * 品牌id
	 */
    private Long brandId;

	/**
	 * 默认图片
	 */
    private String skuDefaultImg;

	/**
	 * 标题
	 */
    private String skuTitle;

	/**
	 * 副标题
	 */
    private String skuSubtitle;

	/**
	 * 价格
	 */
    private BigDecimal price;

	/**
	 * 销量
	 */
    private Long saleCount;

}
