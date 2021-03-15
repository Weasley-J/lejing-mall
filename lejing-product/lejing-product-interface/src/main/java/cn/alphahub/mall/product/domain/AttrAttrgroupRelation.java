package cn.alphahub.mall.product.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
 * @date 2021-02-07 22:46:24
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
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 属性id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long attrId;

    /**
     * 属性分组id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long attrGroupId;

    /**
     * 属性组内排序
     */
    private Integer attrSort;

}
