package cn.alphahub.mall.product.feign;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeckillClientTest {
    @Resource
    private SeckillClient seckillClient;

    @BeforeEach
    void setUp() {
        System.out.println("----------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("----------------------");
    }

    @Test
    void getSkuSeckillInfoBySkuId() {
        BaseResult<SeckillSkuRelation> info = seckillClient.getSkuSeckillInfoBySkuId(1L);
        System.out.println(JSONUtil.toJsonPrettyStr(info));
    }
}
