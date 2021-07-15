package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * spu图片Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SpuImagesService extends IService<SpuImages> {

    void saveBatch(Long spuInfoId, List<String> images);

    /**
     * 查询spu图片分页列表
     *
     * @param pageDomain 分页数据
     * @param spuImages  分页对象
     * @return spu图片分页数据
     */
    PageResult<SpuImages> queryPage(PageDomain pageDomain, SpuImages spuImages);
}
