package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 商品三级分类Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 查询商品三级分类列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param category    商品三级分类,查询字段选择性传入,默认为等值查询
     * @return 商品三级分类分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<Category>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Category category
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Category> pageResult = categoryService.queryPage(pageDomain, category);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.success(pageResult);
        }
        return Result.error(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 查出所有分类及其子分类， 树形结构组装
     *
     * @return 分类及其子分类树形数据
     */
    @GetMapping("/list/tree")
    public Result<List<Category>> listCategoryTree() {
        List<Category> categories = categoryService.listWithTree();
        return Result.ok(categories);
    }

    /**
     * 获取商品三级分类详情
     *
     * @param catId 商品三级分类主键id
     * @return 商品三级分类详细信息
     */
    @GetMapping("/info/{catId}")
    public Result<Category> info(@PathVariable("catId") Long catId) {
        Category category = categoryService.getById(catId);
        return ObjectUtils.anyNotNull(category) ? Result.ok(category) : Result.fail();
    }

    /**
     * 新增商品三级分类
     *
     * @param category 商品三级分类元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        return Result.ok(save);
    }

    /**
     * 级联更新-商品三级分类
     *
     * @param category 商品三级分类,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Category category) {
        boolean update = categoryService.updateCasecade(category);
        return Result.ok(update);
    }

    /**
     * 批量修改三级分类排序
     *
     * @param categories 商品三级分类集合 ,根据id批量更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update/sort")
    public Result<Boolean> updateSort(@RequestBody Category[] categories) {
        boolean update = categoryService.updateBatchById(Arrays.asList(categories));
        return Result.ok(update);
    }

    /**
     * 批量删除商品三级分类
     *
     * @param catIds 商品三级分类id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{catIds}")
    public Result<Boolean> delete(@PathVariable Long[] catIds) {
        boolean delete = categoryService.removeMenusByIds(Arrays.asList(catIds));
        return Result.ok(delete);
    }
}
