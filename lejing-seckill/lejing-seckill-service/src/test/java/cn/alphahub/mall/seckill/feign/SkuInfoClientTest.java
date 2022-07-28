package cn.alphahub.mall.seckill.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SkuInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SkuInfoClientTest {
    @Resource
    SkuInfoClient skuInfoClient;

    @BeforeEach
    void setUp() {
        System.out.println("----------------------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("----------------------------------------");
    }

    @Test
    void info() {
        Result<SkuInfo> info = skuInfoClient.info(9L);
        assertNotNull(info);
        System.err.println(info);
    }
}
