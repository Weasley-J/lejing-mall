package cn.alphahub.mall.seckill.service;

import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;

import java.util.List;

/**
 * 秒杀接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
public interface SeckillService {
    /**
     * 上架最近三天的秒杀商品
     * <p>不处理重复上架</p>
     */
    void onShelveSeckillSkuLatest3Days();

    /**
     * 获取当前时间参与秒杀的商品
     *
     * @return 当前参与秒杀的商品
     */
    List<SeckillSkuRelation> getCurrentSeckillSkus();
}
