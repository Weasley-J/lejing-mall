package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.vo.AttrGroupWithAttrsVO;
import cn.alphahub.mall.product.vo.SpuItemAttrGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 属性分组Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface AttrGroupService extends IService<AttrGroup> {
    /**
     * 查询属性分组分页列表
     *
     * @param pageDomain 分页数据
     * @param attrGroup  分页对象
     * @return 属性分组分页数据
     */
    PageResult<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup);

    /**
     * 根据catalogId查询属性分组列表
     *
     * @param pageDomain 分页数据实体
     * @param attrGroup  属性分组
     * @param key        检索关键字
     * @return 属性分组分页数据
     */
    PageResult<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup, String key);

    /**
     * 获取分类下所有分组&关联属性
     *
     * @param catelogId 分类id
     * @return 分类下所有分组&关联属性列表
     */
    List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    /**
     * 根据商品spuId获取商品sku属性组
     *
     * @param catalogId 三级分类id
     * @param spuId     商品spuId
     * @return 商品sku属性列表
     */
    List<SpuItemAttrGroupVO> listBySpuIdAndCatalogId(Long catalogId, Long spuId);
}
