package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillPromotion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀活动Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SeckillPromotionService extends IService<SeckillPromotion> {

    /**
     * 查询秒杀活动分页列表
     *
     * @param pageDomain       分页数据
     * @param seckillPromotion 分页对象
     * @return 秒杀活动分页数据
     */
    PageResult<SeckillPromotion> queryPage(PageDomain pageDomain, SeckillPromotion seckillPromotion);

}
