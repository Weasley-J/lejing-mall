package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.service.SeckillSessionService;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SeckillSessionServiceImplTest {
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
        List<SeckillSession> session = seckillSessionService.getLatest3DaysSeckillSession();
        Console.error(JSONUtil.toJsonPrettyStr(session));
    }
}
