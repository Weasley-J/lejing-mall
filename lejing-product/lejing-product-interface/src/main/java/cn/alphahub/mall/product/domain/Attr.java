package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品属性
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_attr")
public class Attr implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
    @TableId
    private Long attrId;

	/**
	 * 属性名
	 */
    private String attrName;

	/**
	 * 是否需要检索[0-不需要，1-需要]
	 */
    private Integer searchType;

	/**
	 * 属性图标
	 */
    private String icon;

	/**
	 * 可选值列表[用逗号分隔]
	 */
    private String valueSelect;

	/**
	 * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
	 */
    private Integer attrType;

	/**
	 * 启用状态[0 - 禁用，1 - 启用]
	 */
    private Long enable;

	/**
	 * 所属分类
	 */
    private Long catelogId;

	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
	 */
    private Integer showDesc;

	/**
	 * 单选 多选[0 1]
	 */
    private String valueType;

}
