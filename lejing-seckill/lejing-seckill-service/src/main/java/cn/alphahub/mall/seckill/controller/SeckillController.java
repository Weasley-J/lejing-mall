package cn.alphahub.mall.seckill.controller;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.seckill.service.SeckillService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {
    @Resource
    private SeckillService seckillService;

    /**
     * 上架最近三天的秒杀商品
     *
     * @return ok
     */
    @ResponseBody
    @PostMapping("/on/shelve/seckill/sku/latest/3days")
    public Result<Void> onShelveSeckillSkuLatest3Days() {
        seckillService.onShelveSeckillSkuLatest3Days();
        return Result.ok();
    }

    /**
     * 获取当前时间参与秒杀的商品
     *
     * @return 当前参与秒杀的商品
     */
    @ResponseBody
    @GetMapping("/current/can/seckill/skus")
    public Result<List<SeckillSkuRelation>> getCurrentSeckillSkus() {
        log.info("获取当前时间参与秒杀的商品");
        List<SeckillSkuRelation> skuRelations = seckillService.getCurrentSeckillSkus();
        log.info("返回数据：{}", JSONUtil.toJsonPrettyStr(skuRelations));
        return Result.ok(skuRelations);
    }

    /**
     * 查询商品是否参加秒杀活动
     *
     * @param skuId 商品skuId
     * @return 商品是否参加秒杀信息
     */
    @ResponseBody
    @GetMapping("/sku/info/{skuId}")
    public Result<SeckillSkuRelation> getSkuSeckillInfoBySkuId(@PathVariable("skuId") Long skuId) {
        SeckillSkuRelation skuRelation = seckillService.getSkuSeckillInfoBySkuId(skuId);
        return Result.ok(skuRelation);
    }

    /**
     * 商品进行秒杀(秒杀开始)
     * <p>查看表: oms_order_item</p>
     *
     * @param killId 秒杀id
     * @param key    秒杀随机码
     * @param num    数量
     * @return 视图html
     * @page
     */
    @GetMapping(value = "/kill")
    public String seckill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model
    ) {
        try {
            //1、判断是否登录
            String orderSn = seckillService.kill(killId, key, num);
            if (StringUtils.isNotBlank(orderSn)) {
                model.addAttribute("orderSn", orderSn);
            }
        } catch (InterruptedException e) {
            log.error("异常:{}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return "success";
    }
}
