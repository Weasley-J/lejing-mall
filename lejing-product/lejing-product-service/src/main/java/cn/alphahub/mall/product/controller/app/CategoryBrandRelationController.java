package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.CategoryBrandRelation;
import cn.alphahub.mall.product.service.CategoryBrandRelationService;
import cn.alphahub.mall.product.vo.BrandVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 品牌分类关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 根据分类id获取品牌列表
     *
     * @param catId 分类id
     * @return 商品id名称列表
     */
    @GetMapping("/brands/list")
    public Result<List<BrandVO>> relationBrandsList(@RequestParam(value = "catId") Long catId) {
        List<BrandVO> brandVOList = categoryBrandRelationService.getBrandsByCatId(catId);
        return Result.ok(brandVOList);
    }

    /**
     * 查询品牌分类关联列表
     *
     * @param page                  当前页码,默认第1页
     * @param rows                  显示行数,默认10条
     * @param orderColumn           排序排序字段,默认不排序
     * @param isAsc                 排序方式,desc或者asc
     * @param categoryBrandRelation 品牌分类关联,查询字段选择性传入,默认为等值查询
     * @return 品牌分类关联分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<CategoryBrandRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CategoryBrandRelation categoryBrandRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CategoryBrandRelation> pageResult = categoryBrandRelationService.queryPage(pageDomain, categoryBrandRelation);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取当前品牌关联的分类列表
     *
     * @param brandId 品牌id
     * @return 品牌分类关联列表
     */
    @GetMapping("/catelog/list")
    public Result<List<CategoryBrandRelation>> catelogList(@RequestParam(value = "brandId") Long brandId) {
        QueryWrapper<CategoryBrandRelation> wrapper = new QueryWrapper<CategoryBrandRelation>();
        wrapper.lambda().eq(CategoryBrandRelation::getBrandId, brandId);
        List<CategoryBrandRelation> categoryBrandRelations = categoryBrandRelationService.list(wrapper);
        return Result.ok(categoryBrandRelations);
    }

    /**
     * 获取品牌分类关联详情
     *
     * @param id 品牌分类关联主键id
     * @return 品牌分类关联详细信息
     */
    @GetMapping("/info/{id}")
    public Result<CategoryBrandRelation> info(@PathVariable("id") Long id) {
        CategoryBrandRelation categoryBrandRelation = categoryBrandRelationService.getById(id);
        return ObjectUtils.anyNotNull(categoryBrandRelation) ? Result.ok(categoryBrandRelation) : Result.fail();
    }

    /**
     * 新增品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        boolean save = categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return Result.ok(save);
    }

    /**
     * 修改品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        boolean update = categoryBrandRelationService.updateById(categoryBrandRelation);
        return Result.ok(update);
    }

    /**
     * 批量删除品牌分类关联
     *
     * @param ids 品牌分类关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = categoryBrandRelationService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
