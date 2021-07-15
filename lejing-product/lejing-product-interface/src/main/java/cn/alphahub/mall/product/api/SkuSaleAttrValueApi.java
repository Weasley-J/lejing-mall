package cn.alphahub.mall.product.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * sku销售属性&值 - feign远程调用顶层接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RequestMapping("product/skusaleattrvalue")
public interface SkuSaleAttrValueApi {

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
    BaseResult<PageResult<SkuSaleAttrValue>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuSaleAttrValue skuSaleAttrValue
    );

    /**
     * 根据skuId获取商品的销售属性
     *
     * @param skuId 商品skuId
     * @return 商品的销售属性列表
     */
    @GetMapping("/skuAttrValues/{skuId}")
    BaseResult<List<String>> getSkuAttrValues(@PathVariable("skuId") Long skuId);

    /**
     * 获取sku销售属性&值详情
     *
     * @param id sku销售属性&值主键id
     * @return sku销售属性&值详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<SkuSaleAttrValue> info(@PathVariable("id") Long id);

    /**
     * 新增sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 修改sku销售属性&值
     *
     * @param skuSaleAttrValue sku销售属性&值,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody SkuSaleAttrValue skuSaleAttrValue);

    /**
     * 批量删除sku销售属性&值
     *
     * @param ids sku销售属性&值id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
