package cn.alphahub.mall.product.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@SpringBootTest
class SpuBoundsClientTest {

    @Mock
    @Resource
    private SpuBoundsClient spuBoundsClient;

    @BeforeEach
    void setUp() {
        System.out.println("--------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------------------");
    }

    @Test
    void testSave() {
        SpuBounds spuBounds = new SpuBounds();
        spuBounds.setSpuId(10068L);
        spuBounds.setGrowBounds(new BigDecimal("100"));
        spuBounds.setBuyBounds(new BigDecimal("150"));
        spuBounds.setWork(1);
        Result<Boolean> save = spuBoundsClient.save(spuBounds);
        System.out.println(save);
    }

    @Test
    void testInfo() {
        Result<SpuBounds> info = spuBoundsClient.info(1L);
        System.out.println(info.getCode());
        System.out.println(info.getMessage());
        System.out.println(info.getData());
    }
}
