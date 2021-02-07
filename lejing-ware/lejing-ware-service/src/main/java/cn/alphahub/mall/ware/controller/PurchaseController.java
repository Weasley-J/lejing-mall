package cn.alphahub.mall.ware.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.domain.Purchase;
import cn.alphahub.mall.ware.service.PurchaseService;

import java.util.Arrays;

/**
 * 采购信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController extends BaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 查询采购信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param purchase 采购信息,字段选择性传入,默认为等值查询
     * @return 采购信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:purchase:list")
    public BaseResult<PageResult<Purchase>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Purchase purchase
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Purchase> pageResult = purchaseService.queryPage(pageDomain, purchase);
        return (BaseResult<PageResult<Purchase>>) toPageableResult(pageResult);
    }

    /**
     * 获取采购信息详情
     *
     * @param id 采购信息主键id
     * @return 采购信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:purchase:info")
    public BaseResult<Purchase> info(@PathVariable("id") Long id){
        Purchase purchase = purchaseService.getById(id);
        return (BaseResult<Purchase>) toResponseResult(purchase);
    }

    /**
     * 新增采购信息
     *
     * @param purchase 采购信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    public BaseResult<Boolean> save(@RequestBody Purchase purchase) {
        boolean save = purchaseService.save(purchase);
        return toOperationResult(save);
    }

    /**
     * 修改采购信息
     *
     * @param purchase 采购信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public BaseResult<Boolean> update(@RequestBody Purchase purchase) {
        boolean update = purchaseService.updateById(purchase);
        return toOperationResult(update);
    }

    /**
     * 批量删除采购信息
     *
     * @param ids 采购信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("ware:purchase:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = purchaseService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
