package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * spu信息介绍Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SpuInfoDescService extends IService<SpuInfoDesc> {

    /**
     * 查询spu信息介绍分页列表
     *
     * @param pageDomain  分页数据
     * @param spuInfoDesc 分页对象
     * @return spu信息介绍分页数据
     */
    PageResult<SpuInfoDesc> queryPage(PageDomain pageDomain, SpuInfoDesc spuInfoDesc);

    void saveSpuInfoDesc(SpuInfoDesc spuInfoDesc);
}
