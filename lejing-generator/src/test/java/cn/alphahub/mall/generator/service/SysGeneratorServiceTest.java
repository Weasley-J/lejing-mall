package cn.alphahub.mall.generator.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.Resource;
import java.util.Properties;

@SpringBootTest
class SysGeneratorServiceTest {

    @Resource
    private SysGeneratorService sysGeneratorService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * 获取Nacos中的代码生成配置信息
     */
    @Test
    void getGeneratorProperties() {
        Properties properties = sysGeneratorService.getGeneratorProperties();
    }

    /**
     * 获取classpath下的代码生成配置信息
     */
    @Test
    @SneakyThrows
    void getLoadAllProperties() {
        Properties allProperties = PropertiesLoaderUtils.loadAllProperties("generator.properties");
    }

    @Test
    @SneakyThrows
    void testIdWorker() {
        long id = IdWorker.getId();
        System.out.println(id);
        id = IdWorker.getId();
        System.out.println(id);
        id = IdWorker.getId();
        System.out.println(id);
        id = IdWorker.getId();
        System.out.println(id);
    }
}
