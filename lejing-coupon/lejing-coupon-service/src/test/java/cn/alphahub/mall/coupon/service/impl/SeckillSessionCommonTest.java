package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.mall.coupon.service.SeckillSessionService;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

class SeckillSessionCommonTest {
    @Autowired
    private SeckillSessionService seckillSessionService;

    @BeforeEach
    void setUp() {
        System.out.println("--------------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------------------------------");
    }

    @Test
    void getLatest3DaysSeckillSession() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime offset = LocalDateTimeUtil.offset(now, 3, ChronoUnit.DAYS);

        LocalDateTime start = LocalDateTime.of(LocalDate.from(now), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.from(offset), LocalTime.MAX);

        String startTime = LocalDateTimeUtil.format(start, "yyyy-MM-dd HH:mm:ss");
        String endTime = LocalDateTimeUtil.format(end, "yyyy-MM-dd HH:mm:ss");

        System.err.println(startTime);
        System.err.println(endTime);
    }
}
