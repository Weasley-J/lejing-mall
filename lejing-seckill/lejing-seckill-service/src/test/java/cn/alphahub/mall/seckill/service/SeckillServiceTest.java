package cn.alphahub.mall.seckill.service;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.seckill.feign.SeckillSessionClient;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @BeforeEach
    void setUp() {
        System.out.println("-----------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------------------------");
    }

    @Test
    void getSkuSeckillInfoBySkuId() {
        SeckillSkuRelation relation = seckillService.getSkuSeckillInfoBySkuId(25L);
        assertNotNull(relation);
        System.out.println(JSONUtil.toJsonStr(relation));
    }

    /**
     * 生成近2小时内的秒杀场次
     */
    @SneakyThrows
    @Test
    void genSessions() {
        BaseResult<PageResult<SeckillSession>> result = seckillSessionClient.list();
        List<SeckillSession> objects = Lists.newArrayList();
        if (result.getSuccess().equals(true)) {
            Console.error(JSONUtil.toJsonPrettyStr(result));
            List<SeckillSession> items = result.getData().getItems();
            if (CollectionUtils.isNotEmpty(items)) {
                for (SeckillSession item : items) {
                    long currentTime = System.currentTimeMillis();
                    long start = item.getStartTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant().toEpochMilli();
                    long end = item.getEndTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant().toEpochMilli();
                    boolean b = start <= currentTime && currentTime <= end;
                    if (!b) {
                        System.out.println("\n" + objectMapper.writeValueAsString(item));
                        setSessionDeadline(item);
                        objects.add(item);
                    }
                }
            }
        }
        BaseResult<Boolean> batchUpdate = seckillSessionClient.batchUpdate(objects);
        Console.error("\n" + JSONUtil.toJsonPrettyStr(batchUpdate));
    }

    @SneakyThrows
    private void setSessionDeadline(SeckillSession seckillSession) {
        long current = System.currentTimeMillis();
        LocalDateTime currentDateTime = LocalDateTimeUtil.of(current);
        LocalDate localDate = currentDateTime.toLocalDate();
        LocalTime localTime = LocalTime.of(currentDateTime.toLocalTime().getHour(), 0, 0);
        LocalDateTime startTime = LocalDateTime.of(localDate, localTime);
        LocalDateTime endTime = startTime.plusHours(2);
        seckillSession.setName(localTime.getHour() + ":00点场次");
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
