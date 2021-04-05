package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.vo.SkuItemSaleAttrVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * sku销售属性&值Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValue> {

    /**
     * 查询sku销售属性&值分页列表
     *
     * @param pageDomain       分页数据
     * @param skuSaleAttrValue 分页对象
     * @return sku销售属性&值分页数据
     */
    PageResult<SkuSaleAttrValue> queryPage(PageDomain pageDomain, SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 获取spu销售属性组合
     *
     * @param spuId 商品spuId
     * @return 销售属性组合
     */
    List<SkuItemSaleAttrVO> getSaleAttrBySpuId(Long spuId);

    /**
     * 根据skuId获取商品的销售属性
     *
     * @param skuId 商品skuId
     * @return 商品的销售属性列表
     */
    List<String> getSkuAttrValues(Long skuId);
}
