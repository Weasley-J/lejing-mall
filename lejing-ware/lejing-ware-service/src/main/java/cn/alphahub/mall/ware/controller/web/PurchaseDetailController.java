package cn.alphahub.mall.ware.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import cn.alphahub.mall.ware.service.PurchaseDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 仓储采购表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController extends BaseController {
    @Resource
    private PurchaseDetailService purchaseDetailService;

    /**
     * 查询仓储采购表列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param purchaseDetail 仓储采购表, 查询字段选择性传入, 默认为等值查询
     * @param key            检索关键字
     * @return 仓储采购表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<PurchaseDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            PurchaseDetail purchaseDetail,
            @RequestParam(value = "key", defaultValue = "") String key
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<PurchaseDetail> pageResult = purchaseDetailService.queryPage(pageDomain, purchaseDetail, key);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取仓储采购表详情
     *
     * @param id 仓储采购表主键id
     * @return 仓储采购表详细信息
     */
    @GetMapping("/info/{id}")
    public Result<PurchaseDetail> info(@PathVariable("id") Long id) {
        PurchaseDetail purchaseDetail = purchaseDetailService.getById(id);
        return ObjectUtils.anyNotNull(purchaseDetail) ? Result.ok(purchaseDetail) : Result.fail();
    }

    /**
     * 新增仓储采购表
     *
     * @param purchaseDetail 仓储采购表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody PurchaseDetail purchaseDetail) {
        boolean save = purchaseDetailService.save(purchaseDetail);
        return toOperationResult(save);
    }

    /**
     * 修改仓储采购表
     *
     * @param purchaseDetail 仓储采购表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody PurchaseDetail purchaseDetail) {
        boolean update = purchaseDetailService.updateById(purchaseDetail);
        return toOperationResult(update);
    }

    /**
     * 批量删除仓储采购表
     *
     * @param ids 仓储采购表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = purchaseDetailService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
