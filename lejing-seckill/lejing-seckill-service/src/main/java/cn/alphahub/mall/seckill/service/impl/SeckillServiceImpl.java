package cn.alphahub.mall.seckill.service.impl;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
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
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
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
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
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

    /**
     * <p>
     *     <ul>
     *         <li><img src='https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/QQ20210728224701.gif'/></li>
     *     </ul>
     * </p>
     */
    @Override
    public void onShelveSeckillSkuLatest3Days() {
        BaseResult<List<SeckillSession>> result = seckillSessionClient.getLatest3DaysSeckillSession();
        log.info("上架最近三天的秒杀商品:{}", JSONUtil.toJsonPrettyStr(result));
        if (Objects.equals(result.getSuccess(), true)) {
            List<SeckillSession> sessions = result.getData();
            // 缓存秒杀场次信息到redis
            saveSessionToRedis(sessions);
            // 缓存秒杀商品到redis
            saveSkuToRedis(sessions);
        }
    }

    /**
     * 获取当前时间参与秒杀的商品
     * <p>
     * <b style='color: red'>秒杀预览：</b>
     *     <ul>
     *         <li><img style='width: 130px; height: 100px' src='https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210728223854713.png'/></li>
     *     </ul>
     * </p>
     *
     * @return 当前参与秒杀的商品
     */
    @Override
    public List<SeckillSkuRelation> getCurrentSeckillSkus() {
        List<SeckillSkuRelation> skuRelations = new ArrayList<>();
        // 当前时间戳，1970:01:01 00:00:00 开始算起
        long currentTime = System.currentTimeMillis();
        Set<String> keys = stringRedisTemplate.keys(SeckillConstant.CACHE_PREFIX_SESSION + "*");
        if (CollectionUtils.isEmpty(keys)) {
            log.warn("当前时间参与秒杀的商品为空");
            return Lists.newLinkedList();
        }
        for (String key : keys) {
            String content = key.replace(SeckillConstant.CACHE_PREFIX_SESSION, "");
            String[] sessionInterval = content.split("_");
            long start = Long.parseLong(sessionInterval[0]);
            long end = Long.parseLong(sessionInterval[1]);
            LocalDateTime ldt1 = LocalDateTimeUtil.of(start);
            LocalDateTime ldt2 = LocalDateTimeUtil.of(currentTime);
            LocalDateTime ldt3 = LocalDateTimeUtil.of(end);

            System.err.println("秒杀开始时间：" + LocalDateTimeUtil.format(ldt1, YYYY_MM_DD_HH_MM_SS) + "；当前时间：" + LocalDateTimeUtil.format(ldt2, YYYY_MM_DD_HH_MM_SS) + "；秒杀结束时间：" + LocalDateTimeUtil.format(ldt3, YYYY_MM_DD_HH_MM_SS));
            if (start <= currentTime && currentTime <= end) {
                // 获取这个秒杀场次的所有商品
                List<String> ranges = stringRedisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(SeckillConstant.CACHE_PREFIX_SKU);
                List<Object> objects = hashOps.multiGet(new ArrayList<>(Objects.requireNonNull(ranges)));
                skuRelations = Objects.requireNonNull(objects).stream().map(obj -> JSONUtil.toBean(obj.toString(), SeckillSkuRelation.class)).collect(Collectors.toList());
                break;
            }
        }
        return skuRelations;
    }

    @Override
    public SeckillSkuRelation getSkuSeckillInfoBySkuId(Long skuId) {
        log.info("查询商品是否参加秒杀活动:{}", skuId);
        if (Objects.isNull(skuId)) {
            log.warn("商品skuId为null");
            return null;
        }
        SeckillSkuRelation skuRelation = new SeckillSkuRelation();
        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(SeckillConstant.CACHE_PREFIX_SKU);
        Set<Object> keys = hashOps.keys();
        if (CollectionUtils.isEmpty(keys)) {
            log.warn("redis中sku缓存信息为空");
            return skuRelation;
        }
        // 正则表达式进行匹配: 4_25
        String regExp = "\\d_" + skuId;
        for (Object key : keys) {
            if (Pattern.matches(regExp, String.valueOf(key))) {
                Object redisObj = hashOps.get(key);
                skuRelation = JSONUtil.toBean(String.valueOf(redisObj), SeckillSkuRelation.class);
                long currentTime = System.currentTimeMillis();
                Long startTime = skuRelation.getStartTime();
                Long endTime = skuRelation.getEndTime();
                System.err.println(MessageFormat.format("秒杀开始时间：{0}；当前时间：{1}；秒杀结束时间：{2}",
                        LocalDateTimeUtil.format(LocalDateTimeUtil.of(startTime), YYYY_MM_DD_HH_MM_SS),
                        LocalDateTimeUtil.format(LocalDateTimeUtil.of(currentTime), YYYY_MM_DD_HH_MM_SS),
                        LocalDateTimeUtil.format(LocalDateTimeUtil.of(endTime), YYYY_MM_DD_HH_MM_SS)));

                boolean isCurrentTimeInSeckillSession = startTime <= currentTime && currentTime <= endTime;
                if (!isCurrentTimeInSeckillSession) {
                    skuRelation.setRandomCode(null);
                }

                return skuRelation;
            }
        }
        return skuRelation;
    }

    /**
     * 缓存场次信息到redis
     *
     * @param sessions 场次信息
     */
    private void saveSessionToRedis(List<SeckillSession> sessions) {
        log.info("缓存场次信息到redis:{}", JSONUtil.toJsonStr(sessions));
        if (CollectionUtils.isEmpty(sessions)) {
            log.warn("场次信息为空");
            return;
        }
        // 缓存活动信息
        sessions.forEach(session -> {
            long startTime = LocalDateTimeUtil.toEpochMilli(session.getStartTime());
            long endTime = LocalDateTimeUtil.toEpochMilli(session.getEndTime());
            String cacheKey = SeckillConstant.CACHE_PREFIX_SESSION + startTime + "_" + endTime;
            List<String> skuIds = session.getSkuRelations().stream()
                    .filter(skuRelation -> Objects.nonNull(skuRelation.getSkuId()))
                    .map(skuRelation -> skuRelation.getPromotionSessionId() + "_" + skuRelation.getSkuId())
                    .collect(Collectors.toList());
            log.info("秒杀场次信息：{}；skuIds: {}", JSONUtil.toJsonStr(session), JSONUtil.toJsonStr(skuIds));
            //判断Redis中是否有该信息，如果没有才进行添加
            if (CollectionUtils.isNotEmpty(skuIds) && Objects.equals(stringRedisTemplate.hasKey(cacheKey), false)) {
                stringRedisTemplate.opsForList().leftPushAll(cacheKey, skuIds);
            }
        });
    }

    /**
     * 缓存场次商品到redis
     *
     * @param sessions 场次信息
     */
    private void saveSkuToRedis(List<SeckillSession> sessions) {
        log.info("缓存场次sku到redis:{}", JSONUtil.toJsonStr(sessions));
        if (CollectionUtils.isEmpty(sessions)) {
            log.warn("场次商品为空");
            return;
        }
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SeckillConstant.CACHE_PREFIX_SKU);
        sessions.forEach(seckillSession -> seckillSession.getSkuRelations().forEach(skuRelation -> {
            // 判断Redis中是否有该数据，如果没有才进行添加
            String hashKey = skuRelation.getPromotionSessionId() + "_" + skuRelation.getSkuId();
            if (Objects.equals(ops.hasKey(hashKey), false)) {
                // 1. 缓存秒杀商品
                BaseResult<SkuInfo> result = skuInfoClient.info(skuRelation.getSkuId());
                if (result.getSuccess().equals(true)) {
                    skuRelation.setSkuInfo(beanUtil.copy(result.getData()));
                }
                // 2. sku的秒杀数据
                skuRelation.setStartTime(LocalDateTimeUtil.toEpochMilli(seckillSession.getStartTime()));
                skuRelation.setEndTime(LocalDateTimeUtil.toEpochMilli(seckillSession.getEndTime()));
                // 3. 设置商品的秒杀随机码; seckill?skuId=1&key=RandomCode
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
