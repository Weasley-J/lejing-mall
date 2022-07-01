package cn.alphahub.mall.member.feign;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.Coupon;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CouponClientTest {

    @Autowired
    private CouponClient couponClient;

    @BeforeEach
    void setUp() {
        log.info("---------------------------");
    }

    @AfterEach
    void tearDown() {
        log.info("---------------------------");
    }

    @Test
    void TestCouponClient() {
        Result<Coupon> info = couponClient.info(1L);
        System.out.println(JSONUtil.toJsonPrettyStr(info));
    }
}
