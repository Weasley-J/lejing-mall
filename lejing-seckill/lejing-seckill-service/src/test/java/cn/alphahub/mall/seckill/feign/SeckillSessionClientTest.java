package cn.alphahub.mall.seckill.feign;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SeckillSessionClientTest {
    @Resource
    private SeckillSessionClient seckillSessionClient;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLatest3DaysSeckillSession() {
        BaseResult<List<SeckillSession>> seckillSession = seckillSessionClient.getLatest3DaysSeckillSession();
        System.out.println(JSONUtil.toJsonPrettyStr(seckillSession));
    }
}
