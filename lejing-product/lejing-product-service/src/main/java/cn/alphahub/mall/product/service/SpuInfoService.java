package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.product.vo.SpuSaveVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * spu信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SpuInfoService extends IService<SpuInfo> {

    /**
     * 保存商品信息
     *
     * @param spuSaveVO spu数据
     */
    void saveSpuInfo(SpuSaveVO spuSaveVO);

    /**
     * 查询spu信息分页列表
     *
     * @param pageDomain 分页数据
     * @param spuInfo    分页对象
     * @return spu信息分页数据
     */
    PageResult<SpuInfo> queryPage(PageDomain pageDomain, SpuInfo spuInfo);

    /**
     * 查询spu信息列表
     *
     * @param spuInfo   spu信息,查询字段选择性传入,默认为等值查询
     * @param key       检索关键字
     * @param catelogId 三级分类id
     * @param brandId   品牌id
     * @param status    商品状态
     * @return spu信息列表分页数据
     */
    PageResult<SpuInfo> queryPage(PageDomain pageDomain, SpuInfo spuInfo, String key, Integer catelogId, Integer brandId, Integer status);

    /**
     * 上架商品
     *
     * @param spuId 商品spu id
     * @return 成功返回true, 失败返回false
     */
    boolean spuOnShelves(Long spuId);
}
