package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.RefundInfo;
import cn.alphahub.mall.order.service.RefundInfoService;

import java.util.Arrays;

/**
 * 退款信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@RestController
@RequestMapping("order/refundinfo")
public class RefundInfoController extends BaseController {
    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * 查询退款信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param refundInfo 退款信息,字段选择性传入,默认为等值查询
     * @return 退款信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:refundinfo:list")
    public BaseResult<PageResult<RefundInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            RefundInfo refundInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<RefundInfo> pageResult = refundInfoService.queryPage(pageDomain, refundInfo);
        return (BaseResult<PageResult<RefundInfo>>) toPageableResult(pageResult);
    }

    /**
     * 获取退款信息详情
     *
     * @param id 退款信息主键id
     * @return 退款信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:refundinfo:info")
    public BaseResult<RefundInfo> info(@PathVariable("id") Long id){
        RefundInfo refundInfo = refundInfoService.getById(id);
        return (BaseResult<RefundInfo>) toResponseResult(refundInfo);
    }

    /**
     * 新增退款信息
     *
     * @param refundInfo 退款信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:refundinfo:save")
    public BaseResult<Boolean> save(@RequestBody RefundInfo refundInfo) {
        boolean save = refundInfoService.save(refundInfo);
        return toOperationResult(save);
    }

    /**
     * 修改退款信息
     *
     * @param refundInfo 退款信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:refundinfo:update")
    public BaseResult<Boolean> update(@RequestBody RefundInfo refundInfo) {
        boolean update = refundInfoService.updateById(refundInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除退款信息
     *
     * @param ids 退款信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:refundinfo:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = refundInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
