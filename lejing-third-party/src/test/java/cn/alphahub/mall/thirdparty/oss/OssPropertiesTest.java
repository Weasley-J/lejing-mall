package cn.alphahub.mall.thirdparty.oss;

import cn.alphahub.mall.thirdparty.config.OssProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OssPropertiesTest {
    @Resource
    private OssProperties ossProperties;

    @BeforeEach
    void setUp() {
        System.out.println("---------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------------");
    }

    @Test
    void testOssProperties() {
        System.out.println(ossProperties.toString());
    }
}
