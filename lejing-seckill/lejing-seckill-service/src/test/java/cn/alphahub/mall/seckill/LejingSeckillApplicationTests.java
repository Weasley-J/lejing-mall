package cn.alphahub.mall.seckill;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootTest
class LejingSeckillApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime of = LocalDateTimeUtil.of(1627430400000L, TimeZone.getDefault());
        String format = LocalDateTimeUtil.format(of, "yyyy-MM-dd HH:mm:ss");
        System.out.println("format = " + format);
    }
}
