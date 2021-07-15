package cn.alphahub.mall.ware.mapper;

import cn.alphahub.mall.ware.domain.WareSku;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class WareSkuMapperTest {

    @Resource
    private WareSkuMapper wareSkuMapper;

    @BeforeEach
    void setUp() {
        System.err.println();
    }

    @AfterEach
    void tearDown() {
        System.err.println();
    }

    @Test
    void save() {
        WareSku wareSku = new WareSku();
        wareSku.setSkuId(22L);
        wareSku.setWareId(1L);
        wareSku.setStock(100);
        wareSku.setSkuName("Apple iPhone 11 (A2223) 红色 256GB 移动联通电信4G手机 双卡双待");
        wareSku.setStockLocked(0);

        WareSku wareSku1 = new WareSku();
        wareSku1.setSkuId(18L);
        wareSku1.setWareId(1L);
        wareSku1.setStock(100);
        wareSku1.setSkuName("Apple iPhone 11 (A2223) 黄色 128GB 移动联通电信4G手机 双卡双待\"");
        wareSku1.setStockLocked(0);

        WareSku wareSku2 = new WareSku();
        wareSku2.setSkuId(16L);
        wareSku2.setWareId(1L);
        wareSku2.setStock(100);
        wareSku2.setSkuName("Apple iPhone 11 (A2223) 绿色 256GB 移动联通电信4G手机 双卡双待");
        wareSku2.setStockLocked(0);

        ArrayList<WareSku> skus = Lists.newArrayList(wareSku, wareSku1, wareSku2);
        for (WareSku sku : skus) {
            wareSkuMapper.insert(sku);
            System.out.println(JSONUtil.toJsonStr(sku));
        }
    }

}
