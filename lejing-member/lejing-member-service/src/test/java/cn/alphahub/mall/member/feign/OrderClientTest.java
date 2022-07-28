package cn.alphahub.mall.member.feign;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OrderClientTest {

    @Resource
    private OrderClient orderClient;

    @BeforeEach
    void setUp() {
        System.out.println("-------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------------");
    }

    @Test
    void getMemberOrderList() {
        Result<PageResult<OrderVo>> orderList = orderClient.getMemberOrderList(new PageDomain(1, null, null, null));
        System.err.println(JSONUtil.toJsonPrettyStr(orderList));
    }
}
