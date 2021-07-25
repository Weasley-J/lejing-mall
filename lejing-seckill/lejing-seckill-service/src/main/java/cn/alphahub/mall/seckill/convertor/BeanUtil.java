package cn.alphahub.mall.seckill.convertor;

import cn.alphahub.common.to.SkuInfoTo;
import cn.alphahub.mall.product.domain.SkuInfo;
import org.mapstruct.Mapper;

/**
 * 秒杀业务对象属性转换
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
@Mapper(componentModel = "spring")
public interface BeanUtil {
    /**
     * SkuInfo -> SkuInfoTo
     *
     * @param skuInfo sku信息
     * @return SkuInfoTo
     */
    SkuInfoTo copy(SkuInfo skuInfo);
}
