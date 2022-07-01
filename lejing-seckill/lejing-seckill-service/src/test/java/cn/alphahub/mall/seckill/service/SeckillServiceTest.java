package cn.alphahub.mall.seckill.service;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.seckill.feign.SeckillSessionClient;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SeckillServiceTest {

    public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    SeckillService seckillService;
    @Resource
    SeckillSessionClient seckillSessionClient;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        System.out.println("-----------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------------------------");
    }

    @Test
    void testRedisListTtl() {
        String keyPrefix = "lejing:test:list:";
        BoundListOperations<String, String> listOps = stringRedisTemplate.boundListOps(keyPrefix);
        listOps.leftPushAll("1", "2", "3");
        listOps.expire(30L, TimeUnit.SECONDS);
    }

    @Test
    void testRedisHashTtl() {
        String keyPrefix = "lejing:test:hash:";
        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(keyPrefix);
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        hashOps.putAll(map);
        hashOps.expire(30L, TimeUnit.SECONDS);
    }

    @Test
    void getSkuSeckillInfoBySkuId() {
        SeckillSkuRelation relation = seckillService.getSkuSeckillInfoBySkuId(25L);
        assertNotNull(relation);
        System.out.println(JSONUtil.toJsonStr(relation));
    }

    /**
     * 1. 生成近2小时内的秒杀场次
     */
    @SneakyThrows
    @Test
    void genSessions() {
        Result<PageResult<SeckillSession>> result = seckillSessionClient.list();
        List<SeckillSession> objects = Lists.newArrayList();
        if (result.getSuccess().equals(true)) {
            Console.error(JSONUtil.toJsonPrettyStr(result));
            List<SeckillSession> items = result.getData().getItems();
            if (CollectionUtils.isNotEmpty(items)) {

                long currentTime = System.currentTimeMillis();
                LocalDateTime currentDateTime = LocalDateTimeUtil.of(currentTime);
                LocalTime currentLocalTime = currentDateTime.toLocalTime();
                int currentLocalTimeHour = currentLocalTime.getHour();

                for (SeckillSession item : items) {

                    long start = item.getStartTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant().toEpochMilli();
                    long end = item.getEndTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant().toEpochMilli();

                    boolean b = start <= currentTime && currentTime <= end;
                    if (!b) {
                        System.out.println("\n" + objectMapper.writeValueAsString(item));

                        LocalDate localDate = item.getStartTime().toLocalDate();
                        LocalTime localTime = item.getStartTime().toLocalTime();

                        localTime.withHour(currentLocalTimeHour).withMinute(0).withSecond(0);
                        item.setStartTime(LocalDateTime.of(localDate, localTime));

                        setSessionDeadline(item, currentLocalTimeHour);
                        objects.add(item);
                    }
                    currentLocalTimeHour++;
                    if (currentLocalTimeHour > 23) {
                        currentLocalTimeHour = 0;
                    }
                }
            }
        }
        Result<Boolean> batchUpdate = seckillSessionClient.batchUpdate(objects);
        Console.error("\n" + JSONUtil.toJsonPrettyStr(batchUpdate));
    }

    /**
     * 2. 上架最近三天的秒杀商品
     */
    @Test
    void onShelveSeckillSkuLatest3Days() {
        seckillService.onShelveSeckillSkuLatest3Days();
    }

    @SneakyThrows
    private void setSessionDeadline(SeckillSession seckillSession, int hour) {
        LocalDateTime currentDateTime = LocalDateTimeUtil.of(System.currentTimeMillis());
        seckillSession.setCreateTime(currentDateTime);
        LocalDate localDate = currentDateTime.toLocalDate();
        LocalTime localTime = LocalTime.of(hour, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(localDate, localTime);
        LocalDateTime endTime = startTime.plusHours(2);
        seckillSession.setName((hour < 10 ? "0" + hour : "" + hour) + ":00点场次");
        seckillSession.setStartTime(startTime);
        seckillSession.setEndTime(endTime);
        System.err.println(objectMapper.writeValueAsString(seckillSession));
    }
}

class CommonClassTest {

    @Test
    void generateSeckillSession() {
        long current = System.currentTimeMillis();
        LocalDateTime currentDateTime = LocalDateTimeUtil.of(current);
        LocalDate localDate = currentDateTime.toLocalDate();
        LocalTime localTime = LocalTime.of(currentDateTime.toLocalTime().getHour(), 0, 0);
        LocalDateTime startTime = LocalDateTime.of(localDate, localTime);
        LocalDateTime endTime = startTime.plusHours(2);
        System.err.println("当前时间: " + SeckillServiceTest.df.format(currentDateTime));
        System.err.println("开始时间: " + SeckillServiceTest.df.format(startTime));
        System.err.println("结束时间: " + SeckillServiceTest.df.format(endTime));
    }
}
