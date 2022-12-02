package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.service.SkuSaleAttrValueService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * sku销售属性&值Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
public class SkuSaleAttrValueController {
    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 查询sku销售属性&值列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param skuSaleAttrValue sku销售属性&值,查询字段选择性传入,默认为等值查询
     * @return sku销售属性&值分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SkuSaleAttrValue>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuSaleAttrValue skuSaleAttrValue
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuSaleAttrValue> pageResult = skuSaleAttrValueService.queryPage(pageDomain, skuSaleAttrValue);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 根据skuId获取商品的销售属性
     *
     * @param skuId 商品skuId
     * @return 商品的销售属性列表
     */
    @GetMapping("/skuAttrValues/{skuId}")
    public Result<List<String>> getSkuAttrValues(@PathVariable("skuId") Long skuId) {
        List<String> skuAttrValues = skuSaleAttrValueService.getSkuAttrValues(skuId);
        if (ObjectUtils.isNotEmpty(skuAttrValues)) {
            return Result.ok(skuAttrValues);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取sku销售属性&值详情
     *
     * @param id sku销售属性&值主键id
     * @return sku销售属性&值详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SkuSaleAttrValue> info(@PathVariable("id") Long id) {
        SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueService.getById(id);
        return ObjectUtils.anyNotNull(skuSaleAttrValue) ? Result.ok(skuSaleAttrValue) : Result.fail();
    }

    /**
     * 新增sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
        boolean save = skuSaleAttrValueService.save(skuSaleAttrValue);
        return Result.ok(save);
    }

    /**
     * 修改sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
        boolean update = skuSaleAttrValueService.updateById(skuSaleAttrValue);
        return Result.ok(update);
    }

    /**
     * 批量删除sku销售属性&值
     *
     * @param ids sku销售属性&值id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = skuSaleAttrValueService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
