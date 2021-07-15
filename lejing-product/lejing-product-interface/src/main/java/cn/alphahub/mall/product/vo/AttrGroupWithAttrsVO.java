package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import cn.alphahub.mall.product.domain.Attr;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分类下所有分组&关联属性-视图对象
 *
 * @author Weasley J
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttrGroupWithAttrsVO implements Serializable {

    /**
     * 分组id
     */
    @JsonSerialize(using = IdSerializer.class)
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
    @JsonSerialize(using = IdSerializer.class)
    private Long catelogId;

    /**
     * 商品属性列表
     */
    private List<Attr> attrs;

}
