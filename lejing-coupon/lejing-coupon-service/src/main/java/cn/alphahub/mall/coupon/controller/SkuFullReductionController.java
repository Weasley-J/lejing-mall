package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.SkuReductionTo;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import cn.alphahub.mall.coupon.service.SkuFullReductionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 商品满减信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuFullReductionController extends BaseController {
    @Resource
    private SkuFullReductionService skuFullReductionService;

    /**
     * 查询商品满减信息列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param skuFullReduction 商品满减信息, 查询字段选择性传入, 默认为等值查询
     * @return 商品满减信息分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SkuFullReduction>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuFullReduction skuFullReduction
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuFullReduction> pageResult = skuFullReductionService.queryPage(pageDomain, skuFullReduction);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品满减信息详情
     *
     * @param id 商品满减信息主键id
     * @return 商品满减信息详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SkuFullReduction> info(@PathVariable("id") Long id) {
        SkuFullReduction skuFullReduction = skuFullReductionService.getById(id);
        return ObjectUtils.anyNotNull(skuFullReduction) ? BaseResult.ok(skuFullReduction) : BaseResult.fail();
    }

    /**
     * 新增商品满减信息
     *
     * @param skuFullReduction 商品满减信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SkuFullReduction skuFullReduction) {
        boolean save = skuFullReductionService.save(skuFullReduction);
        return toOperationResult(save);
    }

    /**
     * 修改商品满减信息
     *
     * @param skuFullReduction 商品满减信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
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
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = skuFullReductionService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }

    /**
     * 保存满减、优惠信息
     *
     * @param skuReductionTo
     * @return
     */
    @PostMapping("/saveinfo")
    BaseResult<Boolean> saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo) {
        skuFullReductionService.saveSkuReduction(skuReductionTo);
        return BaseResult.ok("保存成功");
    }
}
