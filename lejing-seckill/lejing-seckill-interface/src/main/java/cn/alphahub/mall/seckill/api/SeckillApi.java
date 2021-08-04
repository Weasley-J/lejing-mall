package cn.alphahub.mall.seckill.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 秒杀商品feign顶层接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021年7月29日
 */
@RequestMapping(value = "/seckill")
public interface SeckillApi {

    /**
     * 获取当前时间参与秒杀的商品
     *
     * @return 当前参与秒杀的商品
     */
    @ResponseBody
    @GetMapping("/current/can/seckill/skus")
    BaseResult<List<SeckillSkuRelation>> getCurrentSeckillSkus();

    /**
     * 查询商品是否参加秒杀活动
     *
     * @param skuId 商品skuId
     * @return 商品是否参加秒杀信息
     */
    @ResponseBody
    @GetMapping("/sku/info/{skuId}")
    BaseResult<SeckillSkuRelation> getSkuSeckillInfoBySkuId(@PathVariable("skuId") Long skuId);
}