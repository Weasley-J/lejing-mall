package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 属性分组关联关系-视图对象
 * @author liuwenjing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttrGroupRelationVO {

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
}
