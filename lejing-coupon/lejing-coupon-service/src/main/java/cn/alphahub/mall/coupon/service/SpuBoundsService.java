package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品spu积分设置Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SpuBoundsService extends IService<SpuBounds> {

    /**
     * 查询商品spu积分设置分页列表
     *
     * @param pageDomain 分页数据
     * @param spuBounds  分页对象
     * @return 商品spu积分设置分页数据
     */
    PageResult<SpuBounds> queryPage(PageDomain pageDomain, SpuBounds spuBounds);

}
