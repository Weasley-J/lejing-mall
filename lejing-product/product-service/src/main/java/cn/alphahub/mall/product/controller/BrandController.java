package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.service.BrandService;

import java.util.Arrays;

/**
 * 品牌Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@RestController
@RequestMapping("product/brand")
public class BrandController extends BaseController {
    @Autowired
    private BrandService brandService;

    /**
     * 查询品牌列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param brand 品牌,字段选择性传入,默认等值查询
     * @return 品牌分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:brand:list")
    public BaseResult<PageResult<Brand>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Brand brand
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Brand> pageResult = brandService.queryPage(pageDomain, brand);
        return (BaseResult<PageResult<Brand>>) toPageableResult(pageResult);
    }

    /**
     * 获取品牌详情
     *
     * @param brandId 品牌主键id
     * @return 品牌详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:brand:info")
    public BaseResult<Brand> info(@PathVariable("brandId") Long brandId){
        Brand brand = brandService.getById(brandId);
        return (BaseResult<Brand>) toResponseResult(brand);
    }

    /**
     * 新增品牌
     *
     * @param brand 品牌元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ Brand brand) {
        boolean save = brandService.save(brand);
        return toOperationResult(save);
    }

    /**
     * 修改品牌
     *
     * @param brand 品牌,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ Brand brand) {
        boolean update = brandService.updateById(brand);
        return toOperationResult(update);
    }

    /**
     * 批量删除品牌
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:brand:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] brandIds){
        boolean delete = brandService.removeByIds(Arrays.asList(brandIds));
        return toOperationResult(delete);
    }
}
