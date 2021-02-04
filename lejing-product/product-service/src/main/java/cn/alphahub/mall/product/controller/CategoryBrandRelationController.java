package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.CategoryBrandRelation;
import cn.alphahub.mall.product.service.CategoryBrandRelationService;

import java.util.Arrays;

/**
 * 品牌分类关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController extends BaseController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 查询品牌分类关联列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param categoryBrandRelation 品牌分类关联,字段选择性传入,默认等值查询
     * @return 品牌分类关联分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public BaseResult<PageResult<CategoryBrandRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CategoryBrandRelation categoryBrandRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CategoryBrandRelation> pageResult = categoryBrandRelationService.queryPage(pageDomain, categoryBrandRelation);
        return (BaseResult<PageResult<CategoryBrandRelation>>) toPageableResult(pageResult);
    }

    /**
     * 获取品牌分类关联详情
     *
     * @param id 品牌分类关联主键id
     * @return 品牌分类关联详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<CategoryBrandRelation> info(@PathVariable("id") Long id){
        CategoryBrandRelation categoryBrandRelation = categoryBrandRelationService.getById(id);
        return (BaseResult<CategoryBrandRelation>) toResponseResult(categoryBrandRelation);
    }

    /**
     * 新增品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ CategoryBrandRelation categoryBrandRelation) {
        boolean save = categoryBrandRelationService.save(categoryBrandRelation);
        return toOperationResult(save);
    }

    /**
     * 修改品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ CategoryBrandRelation categoryBrandRelation) {
        boolean update = categoryBrandRelationService.updateById(categoryBrandRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除品牌分类关联
     *
     * @param ids 品牌分类关联id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = categoryBrandRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}