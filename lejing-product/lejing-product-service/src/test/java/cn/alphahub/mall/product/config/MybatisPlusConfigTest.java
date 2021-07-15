package cn.alphahub.mall.product.config;

import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MybatisPlusConfigTest {

    @Resource
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        System.out.println("---------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------------");
    }

    @Test
    void page() {
        Page<Category> page = new Page<>(1, 10);
        Page<Category> categoryPage = categoryService.page(page);

        long current = categoryPage.getCurrent();
        long pages = categoryPage.getPages();
        long size = categoryPage.getSize();
        long total = categoryPage.getTotal();
        List<Category> records = categoryPage.getRecords();

        System.out.println("当前页码 = " + current);
        System.out.println("当前分页总页数 = " + pages);
        System.out.println("每页显示条数 = " + size);
        System.out.println("总条数 = " + total);
        System.out.println("分页对象记录列表 = " + records);
    }
}
