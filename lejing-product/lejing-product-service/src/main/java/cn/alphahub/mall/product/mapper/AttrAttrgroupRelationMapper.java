package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelation> {

    /**
     * 批量删惯关联关系
     *
     * @param relations 属性&属性分组关联列表
     * @return 删除行数
     */
    Integer deleteBatchRelation(@Param("relations") List<AttrAttrgroupRelation> relations);
}
