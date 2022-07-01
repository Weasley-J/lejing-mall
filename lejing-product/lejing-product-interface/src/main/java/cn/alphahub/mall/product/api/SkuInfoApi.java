package cn.alphahub.mall.product.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * sku信息 - feign api
 *
 * @author Weasley J
 * @date 2021-02-24 15:36:31
 */
public interface SkuInfoApi {

    /**
     * 查询sku信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sidx        排序字段
     * @param order       排序方式:asc/desc
     * @param key         检索关键字
     * @param catelogId   三級分類id
     * @param brandId     品牌id
     * @param min         最低價格
     * @param max         最大價格
     * @return sku信息分页数据
     */
    @GetMapping("/product/skuinfo/list")
    Result<PageResult<SkuInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "sidx", defaultValue = "") String sidx,
            @RequestParam(value = "order", defaultValue = "") String order,
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "catelogId", defaultValue = "") Long catelogId,
            @RequestParam(value = "brandId", defaultValue = "") Long brandId,
            @RequestParam(value = "min", defaultValue = "") Long min,
            @RequestParam(value = "max", defaultValue = "") Long max
    );

    /**
     * 获取sku信息详情
     *
     * @param skuId sku信息主键id
     * @return sku信息详细信息
     */
    @GetMapping("/product/skuinfo/info/{skuId}")
    Result<SkuInfo> info(@PathVariable("skuId") Long skuId);

    /**
     * 新增sku信息
     *
     * @param skuInfo sku信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/product/skuinfo/save")
    Result<Boolean> save(@RequestBody SkuInfo skuInfo);

    /**
     * 修改sku信息
     *
     * @param skuInfo sku信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/product/skuinfo/update")
    Result<Boolean> update(@RequestBody SkuInfo skuInfo);

    /**
     * 批量删除sku信息
     *
     * @param skuIds sku信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/product/skuinfo/delete/{skuIds}")
    Result<Boolean> delete(@PathVariable Long[] skuIds);
}
