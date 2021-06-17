package cn.alphahub.mall.product.feign;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.service.SkuInfoService;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class WareSkuClientTest {

    @Resource
    private WareSkuClient wareSkuClient;
    @Resource
    private SkuInfoService skuInfoService;

    @BeforeEach
    void setUp() {
        System.out.println("----------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------------------------");
    }

    @Test
    void getBySkuId() {
        BaseResult<WareSku> info = wareSkuClient.info(5L);
        if (info.getSuccess()) {
            WareSku data = info.getData();
            System.out.println("data = " + data);
        }
        System.out.println("======================");
        BaseResult<PageResult<WareSku>> baseResult = wareSkuClient.listBySkuId(2L);
        if (baseResult.getSuccess()) {
            PageResult<WareSku> data = baseResult.getData();
            List<WareSku> items = data.getItems();
            if (CollectionUtils.isNotEmpty(items)) {
                WareSku wareSku = items.get(0);
                System.out.println(wareSku);
            }
        }
    }

    /**
     * 测试远程查询是否有库存
     */
    @Test
    void testSkuHasStock() {
        BaseResult<List<WareSkuVO>> skuHasStock = wareSkuClient.getSkuHasStock(Arrays.asList(1L, 2L));
        if (skuHasStock.getSuccess()) {
            List<WareSkuVO> data = skuHasStock.getData();
            data.forEach(System.out::println);
        }
    }

    /**
     * 批量加库存
     */
    @Test
    void incStock() {
        List<SkuInfo> infos = skuInfoService.list();
        for (SkuInfo skuInfo : infos) {
            BaseResult<PageResult<WareSku>> result = wareSkuClient.listBySkuId(skuInfo.getSkuId());
            if (!result.getSuccess()) {
                WareSku wareSku = new WareSku();
                wareSku.setSkuId(skuInfo.getSkuId());
                wareSku.setWareId(1L);
                wareSku.setStock(30);
                wareSku.setSkuName(skuInfo.getSkuName());
                wareSku.setStockLocked(0);
                wareSkuClient.save(wareSku);
                System.err.println(JSONUtil.toJsonStr(wareSku));
            }
        }
    }
}
