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
 * 品牌分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_category_brand_relation")
public class CategoryBrandRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 品牌id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long brandId;

    /**
     * 分类id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long catelogId;

    /**
     *
     */
    private String brandName;

    /**
     *
     */
    private String catelogName;

}
