package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品三级分类
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
    @TableId
    private Long catId;

	/**
	 * 分类名称
	 */
    private String name;

	/**
	 * 父分类id
	 */
    private Long parentCid;

	/**
	 * 层级
	 */
    private Integer catLevel;

	/**
	 * 是否显示[0-不显示，1显示]
	 */
    private Integer showStatus;

	/**
	 * 排序
	 */
    private Integer sort;

	/**
	 * 图标地址
	 */
    private String icon;

	/**
	 * 计量单位
	 */
    private String productUnit;

	/**
	 * 商品数量
	 */
    private Integer productCount;

}
