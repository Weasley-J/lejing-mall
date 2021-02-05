package cn.alphahub.mall.ware.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.domain.PurchaseDetail;
import cn.alphahub.mall.ware.service.PurchaseDetailService;

import java.util.Arrays;

/**
 * 仓储采购表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:37:25
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController extends BaseController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 查询仓储采购表列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param purchaseDetail 仓储采购表,字段选择性传入,默认等值查询
     * @return 仓储采购表分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:purchasedetail:list")
    public BaseResult<PageResult<PurchaseDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            PurchaseDetail purchaseDetail
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<PurchaseDetail> pageResult = purchaseDetailService.queryPage(pageDomain, purchaseDetail);
        return (BaseResult<PageResult<PurchaseDetail>>) toPageableResult(pageResult);
    }

    /**
     * 获取仓储采购表详情
     *
     * @param id 仓储采购表主键id
     * @return 仓储采购表详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:purchasedetail:info")
    public BaseResult<PurchaseDetail> info(@PathVariable("id") Long id){
        PurchaseDetail purchaseDetail = purchaseDetailService.getById(id);
        return (BaseResult<PurchaseDetail>) toResponseResult(purchaseDetail);
    }

    /**
     * 新增仓储采购表
     *
     * @param purchaseDetail 仓储采购表元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("ware:purchasedetail:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ PurchaseDetail purchaseDetail) {
        boolean save = purchaseDetailService.save(purchaseDetail);
        return toOperationResult(save);
    }

    /**
     * 修改仓储采购表
     *
     * @param purchaseDetail 仓储采购表,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ PurchaseDetail purchaseDetail) {
        boolean update = purchaseDetailService.updateById(purchaseDetail);
        return toOperationResult(update);
    }

    /**
     * 批量删除仓储采购表
     *
     * @param ids 仓储采购表id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("ware:purchasedetail:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = purchaseDetailService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
