package cn.alphahub.mall.seckill.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.seckill.service.SeckillService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 秒杀商品Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/26
 */
@Slf4j
@RestController
public class SeckillController {
    @Resource
    private SeckillService seckillService;

    /**
     * 获取当前时间参与秒杀的商品
     *
     * @return 当前参与秒杀的商品
     */
    @GetMapping("/current/seckill/skus")
    public BaseResult<List<SeckillSkuRelation>> getCurrentSeckillSkus() {
        log.info("获取当前时间参与秒杀的商品");
        List<SeckillSkuRelation> skuRelations = seckillService.getCurrentSeckillSkus();
        log.info("返回数据：{}", JSONUtil.toJsonPrettyStr(skuRelations));
        return BaseResult.ok(skuRelations);
    }
}
