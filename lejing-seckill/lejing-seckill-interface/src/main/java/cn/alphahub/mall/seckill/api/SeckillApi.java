package cn.alphahub.mall.seckill.api;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 秒杀商品feign顶层接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021年7月29日
 */
public interface SeckillApi {

    /**
     * 上架最近三天的秒杀商品
     *
     * @return ok
     */
    @ResponseBody
    @PostMapping("/seckill/on/shelve/seckill/sku/latest/3days")
    Result<Void> onShelveSeckillSkuLatest3Days();

    /**
     * 获取当前时间参与秒杀的商品
     *
     * @return 当前参与秒杀的商品
     */
    @ResponseBody
    @GetMapping("/seckill/current/can/seckill/skus")
    Result<List<SeckillSkuRelation>> getCurrentSeckillSkus();

    /**
     * 查询商品是否参加秒杀活动
     *
     * @param skuId 商品skuId
     * @return 商品是否参加秒杀信息
     */
    @ResponseBody
    @GetMapping("/seckill/sku/info/{skuId}")
    Result<SeckillSkuRelation> getSkuSeckillInfoBySkuId(@PathVariable("skuId") Long skuId);
}
