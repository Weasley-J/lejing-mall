package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 品牌
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_brand")
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
    @TableId
    private Long brandId;

	/**
	 * 品牌名
	 */
    private String name;

	/**
	 * 品牌logo地址
	 */
    private String logo;

	/**
	 * 介绍
	 */
    private String descript;

	/**
	 * 显示状态[0-不显示；1-显示]
	 */
    private Integer showStatus;

	/**
	 * 检索首字母
	 */
    private String firstLetter;

	/**
	 * 排序
	 */
    private Integer sort;

}
