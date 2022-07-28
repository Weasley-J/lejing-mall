package cn.alphahub.mall.search.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.vo.AttrRespVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class AttrClientTest {

    @Resource
    private AttrClient attrClient;

    @BeforeEach
    void setUp() {
        System.out.println("------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("------------------");
    }

    @Test
    void getById() {
        Result<AttrRespVO> info = attrClient.info(15L);
        if (info.getSuccess()) {
            log.info("远程调用成功...");
            AttrRespVO respVO = info.getData();
            String attrName = respVO.getAttrName();
            Integer attrType = respVO.getAttrType();
            String icon = respVO.getIcon();
            System.out.println("respVO = " + respVO);
        }
    }
}
