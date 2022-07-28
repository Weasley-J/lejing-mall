package cn.alphahub.mall.seckill.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class SeckillSessionClientTest {
    @Resource
    private SeckillSessionClient seckillSessionClient;

    @BeforeEach
    void setUp() {
        System.out.println("---------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------");
    }

    @Test
    void getLatest3DaysSeckillSession() {
        Result<List<SeckillSession>> result = seckillSessionClient.getLatest3DaysSeckillSession();
        System.err.println(JSONUtil.toJsonPrettyStr(result));
        System.err.println("---------------------");
        result.getData().forEach(session -> {
            LocalDateTime startTime = session.getStartTime();
            LocalDateTime endTime = session.getEndTime();
            System.out.println("startTime = " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("endTime = " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.err.println("---------------------");
        });
    }
}
