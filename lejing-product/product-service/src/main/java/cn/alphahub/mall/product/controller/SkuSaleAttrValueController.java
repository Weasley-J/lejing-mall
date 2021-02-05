package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.service.SkuSaleAttrValueService;

import java.util.Arrays;

/**
 * sku销售属性&值Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:32
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
public class SkuSaleAttrValueController extends BaseController {
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 查询sku销售属性&值列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param skuSaleAttrValue sku销售属性&值,字段选择性传入,默认等值查询
     * @return sku销售属性&值分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:skusaleattrvalue:list")
    public BaseResult<PageResult<SkuSaleAttrValue>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuSaleAttrValue skuSaleAttrValue
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuSaleAttrValue> pageResult = skuSaleAttrValueService.queryPage(pageDomain, skuSaleAttrValue);
        return (BaseResult<PageResult<SkuSaleAttrValue>>) toPageableResult(pageResult);
    }

    /**
     * 获取sku销售属性&值详情
     *
     * @param id sku销售属性&值主键id
     * @return sku销售属性&值详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:skusaleattrvalue:info")
    public BaseResult<SkuSaleAttrValue> info(@PathVariable("id") Long id){
        SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueService.getById(id);
        return (BaseResult<SkuSaleAttrValue>) toResponseResult(skuSaleAttrValue);
    }

    /**
     * 新增sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:skusaleattrvalue:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ SkuSaleAttrValue skuSaleAttrValue) {
        boolean save = skuSaleAttrValueService.save(skuSaleAttrValue);
        return toOperationResult(save);
    }

    /**
     * 修改sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ SkuSaleAttrValue skuSaleAttrValue) {
        boolean update = skuSaleAttrValueService.updateById(skuSaleAttrValue);
        return toOperationResult(update);
    }

    /**
     * 批量删除sku销售属性&值
     *
     * @param ids sku销售属性&值id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:skusaleattrvalue:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = skuSaleAttrValueService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
