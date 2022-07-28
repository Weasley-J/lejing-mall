package cn.alphahub.mall.cart.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SkuSaleAttrValueClientTest {

    @Resource
    private SkuSaleAttrValueClient attrValueClient;

    @BeforeEach
    void setUp() {
        System.out.println("----------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------------");
    }

    @Test
    void skuAttrValues() {
        Result<List<String>> result = attrValueClient.getSkuAttrValues(1L);
        String prettyStr = JSONUtil.toJsonPrettyStr(result);
        System.out.println(prettyStr);
    }
}
