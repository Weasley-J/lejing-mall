package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Mapper
public interface AttrMapper extends BaseMapper<Attr> {

    /**
     * 根据spu属性值id检索出是搜索属性的属性id集合
     *
     * @param attrValueIds spu属性值id集合
     * @return 具备搜索属性的属性id集合
     */
    List<Long> querySearchAttrIds(@Param("attrValueIds") List<Long> attrValueIds);
}
