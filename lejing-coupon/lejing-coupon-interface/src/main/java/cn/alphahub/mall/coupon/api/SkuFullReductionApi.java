package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.SkuReductionTo;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品满减信息Controller
 *
 * @author Weasley J
 * @date 2021-02-14 18:57:50
 */
public interface SkuFullReductionApi {
    /**
     * 查询商品满减信息列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param skuFullReduction 商品满减信息,查询字段选择性传入,默认为等值查询
     * @return 商品满减信息分页数据
     */
    @GetMapping("coupon/skufullreduction/list")
    Result<PageResult<SkuFullReduction>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuFullReduction skuFullReduction
    );

    /**
     * 获取商品满减信息详情
     *
     * @param id 商品满减信息主键id
     * @return 商品满减信息详细信息
     */
    @GetMapping("coupon/skufullreduction/info/{id}")
    Result<SkuFullReduction> info(@PathVariable("id") Long id);

    /**
     * 新增商品满减信息
     *
     * @param skuFullReduction 商品满减信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("coupon/skufullreduction/save")
    Result<Boolean> save(@RequestBody SkuFullReduction skuFullReduction);

    /**
     * 修改商品满减信息
     *
     * @param skuFullReduction 商品满减信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("coupon/skufullreduction/update")
    Result<Boolean> update(@RequestBody SkuFullReduction skuFullReduction);

    /**
     * 批量删除商品满减信息
     *
     * @param ids 商品满减信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("coupon/skufullreduction/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);

    /**
     * 保存满减、优惠信息
     *
     * @param skuReductionTo
     * @return
     */
    @PostMapping("coupon/skufullreduction/saveinfo")
    Result<Boolean> saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
