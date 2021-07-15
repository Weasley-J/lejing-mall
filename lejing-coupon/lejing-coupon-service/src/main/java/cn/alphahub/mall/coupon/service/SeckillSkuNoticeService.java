package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀商品通知订阅Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SeckillSkuNoticeService extends IService<SeckillSkuNotice> {

    /**
     * 查询秒杀商品通知订阅分页列表
     *
     * @param pageDomain       分页数据
     * @param seckillSkuNotice 分页对象
     * @return 秒杀商品通知订阅分页数据
     */
    PageResult<SeckillSkuNotice> queryPage(PageDomain pageDomain, SeckillSkuNotice seckillSkuNotice);

}
