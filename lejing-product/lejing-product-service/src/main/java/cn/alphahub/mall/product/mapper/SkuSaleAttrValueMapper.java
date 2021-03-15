package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.vo.SkuItemSaleAttrVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {

    /**
     * 获取spu销售属性组合
     *
     * @param spuId 商品spuId
     * @return 销售属性组合
     */
    List<SkuItemSaleAttrVO> getSaleAttrBySpuId(@Param("spuId") Long spuId);
}
