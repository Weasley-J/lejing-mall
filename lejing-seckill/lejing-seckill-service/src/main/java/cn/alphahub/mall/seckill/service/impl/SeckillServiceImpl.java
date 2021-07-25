package cn.alphahub.mall.seckill.service.impl;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.seckill.constant.SeckillConstant;
import cn.alphahub.mall.seckill.convertor.BeanUtil;
import cn.alphahub.mall.seckill.feign.SeckillSessionClient;
import cn.alphahub.mall.seckill.feign.SkuInfoClient;
import cn.alphahub.mall.seckill.service.SeckillService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.alphahub.mall.seckill.constant.SeckillConstant.CACHE_PREFIX_SKU_STOCK_SEMAPHORE;

/**
 * 秒杀实现
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SeckillSessionClient seckillSessionClient;
    @Resource
    private SkuInfoClient skuInfoClient;
    @Resource
    private BeanUtil beanUtil;
    @Resource
    private RedissonClient redissonClient;

    @Override
    public void onShelveSeckillSkuLatest3Days() {
        BaseResult<List<SeckillSession>> result = seckillSessionClient.getLatest3DaysSeckillSession();
        log.info("上架最近三天的秒杀商品:{}", JSONUtil.toJsonPrettyStr(result));
        if (result.getSuccess().equals(true)) {
            List<SeckillSession> sessions = result.getData();
            // 缓存场次信息到redis
            saveSessionToRedis(sessions);
            // 缓存场次sku到redis
            saveSkuToRedis(sessions);
        }
    }

    /**
     * 缓存场次信息到redis
     *
     * @param sessions 场次信息
     */
    private void saveSessionToRedis(List<SeckillSession> sessions) {
        log.info("缓存场次信息到redis:{}", JSONUtil.toJsonStr(sessions));
        if (CollectionUtils.isNotEmpty(sessions)) {
            // 缓存活动信息
            sessions.forEach(session -> {
                long startTime = LocalDateTimeUtil.toEpochMilli(session.getStartTime());
                long endTime = LocalDateTimeUtil.toEpochMilli(session.getEndTime());
                String cacheKey = SeckillConstant.CACHE_PREFIX_SESSION + startTime + "_" + endTime;
                List<String> skuIds = session.getSkuRelations().stream()
                        .filter(skuRelation -> Objects.nonNull(skuRelation.getSkuId()))
                        .map(skuRelation -> skuRelation.getPromotionSessionId() + "_" + skuRelation.getSkuId().toString())
                        .collect(Collectors.toList());
                log.info("秒杀场次信息：{}；skuIds: {}", JSONUtil.toJsonStr(session), JSONUtil.toJsonStr(skuIds));
                //判断Redis中是否有该信息，如果没有才进行添加
                if (CollectionUtils.isNotEmpty(skuIds) && Objects.equals(stringRedisTemplate.hasKey(cacheKey), false)) {
                    stringRedisTemplate.opsForList().leftPushAll(cacheKey, skuIds);
                }
            });
        }
    }

    /**
     * 缓存场次商品到redis
     *
     * @param sessions 场次信息
     */
    private void saveSkuToRedis(List<SeckillSession> sessions) {
        log.info("缓存场次sku到redis:{}", JSONUtil.toJsonStr(sessions));
        if (CollectionUtils.isNotEmpty(sessions)) {

            BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SeckillConstant.CACHE_PREFIX_SKU);

            sessions.forEach(seckillSession -> seckillSession.getSkuRelations().forEach(skuRelation -> {
                String hashKey = skuRelation.getPromotionSessionId() + "_" + skuRelation.getSkuId().toString();
                if (Objects.equals(ops.hasKey(hashKey), false)) {
                    // 1. 缓存秒杀商品
                    BaseResult<SkuInfo> result = skuInfoClient.info(skuRelation.getSkuId());
                    if (result.getSuccess().equals(true)) {
                        skuRelation.setSkuInfo(beanUtil.copy(result.getData()));
                    }
                    // 2. sku的秒杀数据
                    skuRelation.setStartTime(LocalDateTimeUtil.toEpochMilli(seckillSession.getStartTime()));
                    skuRelation.setEndTime(LocalDateTimeUtil.toEpochMilli(seckillSession.getEndTime()));
                    // 3. 设置商品的秒杀随机码; seckill?skuId=1&&key=RandomCode
                    String randomToken = IdUtil.fastSimpleUUID();
                    skuRelation.setRandomCode(randomToken);
                    // redis分布式信号量: 将商品可以秒杀的数量（秒杀总量，库存）作为信号量；作用：限流
                    RSemaphore semaphore = redissonClient.getSemaphore(CACHE_PREFIX_SKU_STOCK_SEMAPHORE + randomToken);
                    semaphore.trySetPermits(skuRelation.getSeckillCount().intValue());
                    // 缓存数据到redis
                    ops.put(hashKey, JSONUtil.toJsonStr(skuRelation));
                }
            }));
        }
    }
}
