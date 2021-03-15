package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.vo.SkuItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * sku信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 查询sku信息分页列表
     *
     * @param pageDomain 分页数据
     * @param skuInfo    分页对象
     * @return sku信息分页数据
     */
    PageResult<SkuInfo> queryPage(PageDomain pageDomain, SkuInfo skuInfo);

    /**
     * 查询sku信息列表
     *
     * @param pageDomain Pagehelper分页对象
     * @param key        检索关键字
     * @param catelogId  三級分類id
     * @param brandId    品牌id
     * @param min        最低價格
     * @param max        最大價格
     * @return sku信息分页列表
     */
    PageResult<SkuInfo> queryPage(PageDomain pageDomain, String key, Long catelogId, Long brandId, Long min, Long max);

    /**
     * 根据spuId查出对应的所有sku信息，品牌名字
     *
     * @param spuId spuId
     * @return 所有sku信息
     */
    List<SkuInfo> getSkusBySpuId(Long spuId);

    /**
     * 根据skuId获取商品详情
     *
     * @param skuId 商品skuId
     * @return 商品详情页数据
     */
    SkuItemVO getSkuItemBySkuId(Long skuId);

}
