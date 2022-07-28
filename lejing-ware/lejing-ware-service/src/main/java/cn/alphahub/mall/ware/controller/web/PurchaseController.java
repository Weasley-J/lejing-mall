package cn.alphahub.mall.ware.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.ware.domain.Purchase;
import cn.alphahub.mall.ware.service.PurchaseService;
import cn.alphahub.mall.ware.vo.MergeVo;
import cn.alphahub.mall.ware.vo.PurchaseDoneVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 采购信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Resource
    private PurchaseService purchaseService;

    /**
     * 查询w未领取的采购单列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param purchase    采购信息, 查询字段选择性传入, 默认为等值查询
     * @return 采购信息分页数据
     */
    @GetMapping("/unreceive/list")
    public Result<PageResult<Purchase>> unReceiveList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Purchase purchase
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Purchase> pageResult = purchaseService.unReceiveList(pageDomain, purchase);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 查询采购信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param purchase    采购信息, 查询字段选择性传入, 默认为等值查询
     * @return 采购信息分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<Purchase>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Purchase purchase
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Purchase> pageResult = purchaseService.queryPage(pageDomain, purchase);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取采购信息详情
     *
     * @param id 采购信息主键id
     * @return 采购信息详细信息
     */
    @GetMapping("/info/{id}")
    public Result<Purchase> info(@PathVariable("id") Long id) {
        Purchase purchase = purchaseService.getById(id);
        return ObjectUtils.anyNotNull(purchase) ? Result.ok(purchase) : Result.fail();
    }

    /**
     * 新增采购信息
     *
     * @param purchase 采购信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Purchase purchase) {
        purchase.setCreateTime(new Date());
        boolean save = purchaseService.save(purchase);
        return Result.ok(save);
    }

    /**
     * 合并仓储采购表
     *
     * @param mergeVo 合并采购单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/merge")
    public Result<Boolean> merge(@RequestBody MergeVo mergeVo) {
        boolean save = purchaseService.merge(mergeVo);
        return Result.ok(save);
    }

    /**
     * 领取采购单
     *
     * @param ids 采购单id集合
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/received")
    public Result<Boolean> received(@RequestBody List<Long> ids) {
        boolean save = purchaseService.received(ids);
        return Result.ok(save);
    }

    /**
     * 完成采购
     *
     * @param purchaseDoneVo 完成采购的参数元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/done")
    public Result<Boolean> done(@RequestBody PurchaseDoneVo purchaseDoneVo) {
        boolean save = purchaseService.done(purchaseDoneVo);
        return Result.ok(save);
    }

    /**
     * 修改采购信息
     *
     * @param purchase 采购信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Purchase purchase) {
        boolean update = purchaseService.updateById(purchase);
        return Result.ok(update);
    }

    /**
     * 批量删除采购信息
     *
     * @param ids 采购信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = purchaseService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
