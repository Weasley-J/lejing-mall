package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import cn.alphahub.mall.coupon.service.SkuFullReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 商品满减信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuFullReductionController extends BaseController {
    @Autowired
    private SkuFullReductionService skuFullReductionService;

    /**
     * 查询商品满减信息列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param skuFullReduction 商品满减信息,字段选择性传入,默认为等值查询
     * @return 商品满减信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:skufullreduction:list")
    public BaseResult<PageResult<SkuFullReduction>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuFullReduction skuFullReduction
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuFullReduction> pageResult = skuFullReductionService.queryPage(pageDomain, skuFullReduction);
        return (BaseResult<PageResult<SkuFullReduction>>) toPageableResult(pageResult);
    }

    /**
     * 获取商品满减信息详情
     *
     * @param id 商品满减信息主键id
     * @return 商品满减信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:skufullreduction:info")
    public BaseResult<SkuFullReduction> info(@PathVariable("id") Long id) {
        SkuFullReduction skuFullReduction = skuFullReductionService.getById(id);
        return (BaseResult<SkuFullReduction>) toResponseResult(skuFullReduction);
    }

    /**
     * 新增商品满减信息
     *
     * @param skuFullReduction 商品满减信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:skufullreduction:save")
    public BaseResult<Boolean> save(@RequestBody SkuFullReduction skuFullReduction) {
        boolean save = skuFullReductionService.save(skuFullReduction);
        return toOperationResult(save);
    }

    /**
     * 修改商品满减信息
     *
     * @param skuFullReduction 商品满减信息,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:skufullreduction:update")
    public BaseResult<Boolean> update(@RequestBody SkuFullReduction skuFullReduction) {
        boolean update = skuFullReductionService.updateById(skuFullReduction);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品满减信息
     *
     * @param ids 商品满减信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:skufullreduction:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = skuFullReductionService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
