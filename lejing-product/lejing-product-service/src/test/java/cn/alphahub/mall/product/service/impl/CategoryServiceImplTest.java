package cn.alphahub.mall.product.service.impl;

import cn.alphahub.mall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class CategoryServiceImplTest {

    @Resource
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        System.out.println("-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("------------------------");
    }

    @Test
    void getCatelogFullPath() {
        Long[] catelogFullPath = categoryService.getCategoryFullPath(225L);
        for (Long id : catelogFullPath) {
            log.info("目录全路径: {}", id);
        }
    }
}
