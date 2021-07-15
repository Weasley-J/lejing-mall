package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * sku图片Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SkuImagesService extends IService<SkuImages> {

    /**
     * 查询sku图片分页列表
     *
     * @param pageDomain 分页数据
     * @param skuImages  分页对象
     * @return sku图片分页数据
     */
    PageResult<SkuImages> queryPage(PageDomain pageDomain, SkuImages skuImages);

    /**
     * 获取skuId的对应的图片列表
     *
     * @param skuId 商品skuId
     * @return 图片集合
     */
    List<SkuImages> getImagesBySkuId(Long skuId);
}
