package cn.alphahub.mall.ware.feign;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JsonUtil;
import cn.alphahub.mall.order.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class OrderClientTest {

    @Resource
    private OrderClient orderClient;

    private long skuId = 1364966015422816258L;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach --------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach --------------------");
    }

    @Test
    void order() {
        BaseResult<Order> info = orderClient.info(1L);
        System.out.println(JsonUtil.toJsonStr(info));
    }
}
