package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.service.SkuInfoService;

import java.util.Arrays;

/**
 * sku信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController extends BaseController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 查询sku信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param skuInfo sku信息,字段选择性传入,默认为等值查询
     * @return sku信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:skuinfo:list")
    public BaseResult<PageResult<SkuInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuInfo skuInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuInfo> pageResult = skuInfoService.queryPage(pageDomain, skuInfo);
        return (BaseResult<PageResult<SkuInfo>>) toPageableResult(pageResult);
    }

    /**
     * 获取sku信息详情
     *
     * @param skuId sku信息主键id
     * @return sku信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:skuinfo:info")
    public BaseResult<SkuInfo> info(@PathVariable("skuId") Long skuId){
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return (BaseResult<SkuInfo>) toResponseResult(skuInfo);
    }

    /**
     * 新增sku信息
     *
     * @param skuInfo sku信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:skuinfo:save")
    public BaseResult<Boolean> save(@RequestBody SkuInfo skuInfo) {
        boolean save = skuInfoService.save(skuInfo);
        return toOperationResult(save);
    }

    /**
     * 修改sku信息
     *
     * @param skuInfo sku信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("product:skuinfo:update")
    public BaseResult<Boolean> update(@RequestBody SkuInfo skuInfo) {
        boolean update = skuInfoService.updateById(skuInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除sku信息
     *
     * @param skuIds sku信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:skuinfo:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] skuIds){
        boolean delete = skuInfoService.removeByIds(Arrays.asList(skuIds));
        return toOperationResult(delete);
    }
}
