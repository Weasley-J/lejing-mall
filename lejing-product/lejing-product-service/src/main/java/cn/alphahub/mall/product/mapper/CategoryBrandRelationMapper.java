package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.CategoryBrandRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Mapper
public interface CategoryBrandRelationMapper extends BaseMapper<CategoryBrandRelation> {

    /**
     * 级联更新-商品三级分类
     *
     * @param catId 商品三级分类id
     * @param name  分类名称
     * @return 受影响行数
     */
    int updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
