package cn.alphahub.mall.seckill.schedule;

import cn.alphahub.mall.seckill.constant.SeckillConstant;
import cn.alphahub.mall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * sku秒杀定时任务
 * <p>
 * 秒杀商品定时上架：每条晚上03:00上架
 *     <ul>
 *         <li>今天：00:00:00 - 23:59:59</li>
 *         <li>明天：00:00:00 - 23:59:59</li>
 *         <li>后天：00:00:00 - 23:59:59</li>
 *     </ul>
 * </p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
@Slf4j
@Component
public class SeckillSkuScheduleTask {

    @Resource
    private SeckillService seckillService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 上架最近三天的秒杀商品
     * corn表达式: @Scheduled(cron = "0 0 3 ? * *")
     */
    @Scheduled(cron = "0 0/45 * ? * *")
    public void onShelveSeckillSkuLatest3Days() {
        log.warn("上架最近三天的秒杀商品.");
        RLock rLock = redissonClient.getLock(SeckillConstant.CACHE_PREFIX_SKU_ON_SHELF_LOCK + this.getClass().getTypeName());
        rLock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.onShelveSeckillSkuLatest3Days();
        } finally {
            rLock.unlock();
        }
    }
}
