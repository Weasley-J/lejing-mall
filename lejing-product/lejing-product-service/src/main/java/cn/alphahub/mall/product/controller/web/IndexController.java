package cn.alphahub.mall.product.controller.web;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;
import cn.alphahub.mall.product.vo.SecondCategoryVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>首页Controller</b>
 *
 * @author Weasley J
 * @version 1.0
 * @date 2021/03/09
 */
@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;

    /**
     * 进入首页返回所有1级分类
     *
     * @param model 模型
     * @return 首页视图
     */
    @GetMapping({"/", "index", "index.html"})
    public String indexPageWithFirstLevelCategories(Model model) {
        List<Category> categories = categoryService.getFirstLevelCategories();
        model.addAttribute("categories", categories);
        return "index";
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
        return categoryService.getCatalogJsonFromRedis();
    }

    /**
     * 你好，Weasley J！
     *
     * @return 对Weasley J的问候
     */
    @ResponseBody
    @GetMapping("/hello")
    public BaseResult<String> helloWeasleyJ() {
        return BaseResult.ok("响应成功", "Hello Weasley J!");
    }
}
