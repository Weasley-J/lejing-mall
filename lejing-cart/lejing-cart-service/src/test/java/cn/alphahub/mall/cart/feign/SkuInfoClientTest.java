package cn.alphahub.mall.cart.feign;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SkuInfoClientTest {

    @Resource
    private SkuInfoClient skuInfoClient;

    @BeforeEach
    void setUp() {
        System.out.println("----------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------------");
    }

    @Test
    void info() {
        BaseResult<SkuInfo> info = skuInfoClient.info(1L);
        String prettyStr = JSONUtil.toJsonPrettyStr(info);
        System.out.println(prettyStr);
    }
}
