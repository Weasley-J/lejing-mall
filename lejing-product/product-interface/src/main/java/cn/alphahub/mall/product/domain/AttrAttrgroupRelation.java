package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 属性&属性分组关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 属性id
	 */
    private Long attrId;

	/**
	 * 属性分组id
	 */
    private Long attrGroupId;

	/**
	 * 属性组内排序
	 */
    private Integer attrSort;

}