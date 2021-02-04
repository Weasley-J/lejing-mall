package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 属性分组
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_attr_group")
public class AttrGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
    @TableId
    private Long attrGroupId;

	/**
	 * 组名
	 */
    private String attrGroupName;

	/**
	 * 排序
	 */
    private Integer sort;

	/**
	 * 描述
	 */
    private String descript;

	/**
	 * 组图标
	 */
    private String icon;

	/**
	 * 所属分类id
	 */
    private Long catelogId;

}