package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品属性-响应视图对象
 *
 * @author liuwenjing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AttrRespVO extends AttrVO {
    private static final long serialVersionUID = 1L;
    /**
     * 所属分类名字
     */
    private String catelogName;

    /**
     * 所属分组名字
     */
    private String groupName;

    /**
     * 所属分类路径
     */
    private Long[] catelogPath;
}
