package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀活动商品关联Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelation> {

    /**
     * 查询秒杀活动商品关联分页列表
     *
     * @param pageDomain         分页数据
     * @param seckillSkuRelation 分页对象
     * @return 秒杀活动商品关联分页数据
     */
    PageResult<SeckillSkuRelation> queryPage(PageDomain pageDomain, SeckillSkuRelation seckillSkuRelation);
}
