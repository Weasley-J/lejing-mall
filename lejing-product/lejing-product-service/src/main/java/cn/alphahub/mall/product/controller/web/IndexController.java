package cn.alphahub.mall.product.controller.web;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;
import cn.alphahub.mall.product.vo.SecondCategoryVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 首页Controller
 *
 * @author Weasley J
 * @version 1.0
 * @date 2021/03/09
 */
@Controller
public class IndexController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private CategoryService categoryService;

    /**
     * 进入首页返回所有1级分类
     *
     * @param model 模型
     * @return 首页视图
     * @page
     */
    @GetMapping({"/", "index", "index.html"})
    public String indexPageWithFirstLevelCategories(Model model) {
        List<Category> categories = categoryService.getFirstLevelCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    /**
     * api视图
     *
     * @return api视图
     * @page
     */
    @GetMapping({"/api", "api.html"})
    public String apiDoc() {
        return "../static/index.html";
    }

    /**
     * <b>查出三级分类</b>
     * key-1级分类,value-2级分类List
     *
     * @return 一级分类+二级分类列表集合
     */
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<SecondCategoryVO>> getCatalogJson() {
        return categoryService.getAllLevelCategories();
    }

    /**
     * 你好，Weasley J！
     *
     * @return 对Weasley J的问候
     */
    @ResponseBody
    @GetMapping("/hello")
    public Result<String> helloWeasleyJ() {
        Result<String> result;

        RLock lock = redissonClient.getLock("my:lock");
        // 阻塞等待
        lock.lock();

        try {
            System.out.println("加锁成功：" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName());

            System.out.println("执行业务...");
            result = Result.of("响应成功", "Hello Weasley J!");

        } catch (Exception e) {
            result = Result.fail("响应失败", "Hello Weasley J!");
        } finally {
            System.out.println("释放成功：" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "\n");
            lock.unlock();
        }

        return result;
    }
}
