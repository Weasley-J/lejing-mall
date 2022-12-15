package cn.alphahub.mall.ware.feign;

import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootTest
class SkuInfoClientTest {

    @Resource
    private SkuInfoClient skuInfoClient;

    private long skuId = 1364966015422816258L;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach --------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach --------------------");
    }

    @Order(1)
    @Test
    void testListByGetMapping() {
        log.info("--------------- testListByGetMapping ----------------------");
        Result<PageResult<SkuInfo>> result = skuInfoClient.list(
                1, 20, null, null, null, null, null, null, null, null, null
        );
        PageResult<SkuInfo> data = result.getData();
        Long totalCount = data.getTotalCount();
        Integer totalPage = data.getTotalPage();
        List<SkuInfo> skuInfoList = data.getItems();
        System.out.println("code = " + result.getCode());
        System.out.println("message = " + result.getMessage());
        System.out.println("totalCount = " + totalCount);
        System.out.println("totalPage = " + totalPage);
        System.out.println(" ----------- skuInfoList ---------------");
        skuInfoList.forEach(System.out::println);
    }

    @Order(2)
    @Test
    void testInfoByGetMapping() {
        log.info("--------------- testInfoByGetMapping ----------------------");
        Result<SkuInfo> result = skuInfoClient.info(skuId);
        System.out.println("code = " + result.getCode());
        System.out.println("message = " + result.getMessage());
        System.out.println("skuInfo: " + result.getData());
    }

    @Order(3)
    @Test
    void testUpdateByPutMapping() {
        log.info("--------------- testUpdateByPutMapping ----------------------");
        Result<SkuInfo> result;
        result = skuInfoClient.info(skuId);
        System.out.println("code = " + result.getCode());
        System.out.println("message = " + result.getMessage());
        SkuInfo skuInfo = result.getData();
        System.out.println("更新前: " + skuInfo);
        skuInfo.setSkuDesc(DateUtil.now());
        System.out.println("----------------------------------------");
        Result<Boolean> result1 = skuInfoClient.update(skuInfo);
        System.out.println("Result<Boolean>  " + result1.getCode());
        System.out.println("Result<Boolean>  " + result1.getMessage());
        System.out.println("-----------------------------------------");
        result = skuInfoClient.info(skuId);
        System.out.println("code = " + result.getCode());
        System.out.println("message = " + result.getMessage());
        skuInfo = result.getData();
        System.out.println("更新后: " + skuInfo);
    }

    @Order(4)
    @Test
    void testDeleteByDeleteMapping() {
        log.info("--------------- testDeleteByDeleteMapping ----------------------");
        List<Long> ids = Collections.singletonList(skuId);
        Long[] idArr = ids.toArray(new Long[ids.size()]);
        Result<Boolean> delete = skuInfoClient.delete(idArr);
        System.out.println("code = " + delete.getCode());
        System.out.println("message = " + delete.getMessage());
    }
}
