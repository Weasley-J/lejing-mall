package cn.alphahub.mall.search.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Brand;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class BrandClientTest {
    @Resource
    private BrandClient brandClient;

    @BeforeEach
    void setUp() {
        System.out.println("------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("------------------");
    }

    @Test
    void getBrandInfos() {
        List<Long> ids = Arrays.asList(3L);
        Result<List<Brand>> result = brandClient.brandsInfo(ids);
        if (result.getSuccess()) {
            List<Brand> brands = result.getData();
            log.info("远程调用商品服务查询品牌信息成功,响应数据:\n{}", JSONUtil.toJsonPrettyStr(brands));
            String brandNames = brands.stream().map(Brand::getName).collect(Collectors.joining(";"));
            System.out.println("\n" + brandNames);
        }
    }
}
