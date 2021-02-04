package cn.alphahub.mall.ware.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.service.WareSkuService;

import java.util.Arrays;

/**
 * 商品库存Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:22:49
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController extends BaseController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 查询商品库存列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param wareSku 商品库存,字段选择性传入,默认等值查询
     * @return 商品库存分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("ware:waresku:list")
    public BaseResult<PageResult<WareSku>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareSku wareSku
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<WareSku> pageResult = wareSkuService.queryPage(pageDomain, wareSku);
        return (BaseResult<PageResult<WareSku>>) toPageableResult(pageResult);
    }

    /**
     * 获取商品库存详情
     *
     * @param id 商品库存主键id
     * @return 商品库存详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<WareSku> info(@PathVariable("id") Long id){
        WareSku wareSku = wareSkuService.getById(id);
        return (BaseResult<WareSku>) toResponseResult(wareSku);
    }

    /**
     * 新增商品库存
     *
     * @param wareSku 商品库存元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ WareSku wareSku) {
        boolean save = wareSkuService.save(wareSku);
        return toOperationResult(save);
    }

    /**
     * 修改商品库存
     *
     * @param wareSku 商品库存,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ WareSku wareSku) {
        boolean update = wareSkuService.updateById(wareSku);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品库存
     *
     * @param ids 商品库存id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("ware:waresku:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = wareSkuService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}