package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.vo.SpuItemAttrGroupVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Mapper
public interface AttrGroupMapper extends BaseMapper<AttrGroup> {
    /**
     * 根据商品spuId获取商品sku的对应属性组
     *
     * @param catalogId 三级分类id
     * @param spuId     商品spuId
     * @return 商品sku属性列表
     */
    List<SpuItemAttrGroupVO> listBySpuIdAndCatalogId(@Param("catalogId") Long catalogId, @Param("spuId") Long spuId);
}
