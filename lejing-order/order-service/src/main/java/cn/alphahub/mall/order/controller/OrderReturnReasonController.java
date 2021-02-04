package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.alphahub.mall.order.service.OrderReturnReasonService;

import java.util.Arrays;

/**
 * 退货原因Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@RestController
@RequestMapping("order/orderreturnreason")
public class OrderReturnReasonController extends BaseController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 查询退货原因列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param orderReturnReason 退货原因,字段选择性传入,默认等值查询
     * @return 退货原因分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:orderreturnreason:list")
    public BaseResult<PageResult<OrderReturnReason>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderReturnReason orderReturnReason
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderReturnReason> pageResult = orderReturnReasonService.queryPage(pageDomain, orderReturnReason);
        return (BaseResult<PageResult<OrderReturnReason>>) toPageableResult(pageResult);
    }

    /**
     * 获取退货原因详情
     *
     * @param id 退货原因主键id
     * @return 退货原因详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<OrderReturnReason> info(@PathVariable("id") Long id){
        OrderReturnReason orderReturnReason = orderReturnReasonService.getById(id);
        return (BaseResult<OrderReturnReason>) toResponseResult(orderReturnReason);
    }

    /**
     * 新增退货原因
     *
     * @param orderReturnReason 退货原因元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:orderreturnreason:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ OrderReturnReason orderReturnReason) {
        boolean save = orderReturnReasonService.save(orderReturnReason);
        return toOperationResult(save);
    }

    /**
     * 修改退货原因
     *
     * @param orderReturnReason 退货原因,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ OrderReturnReason orderReturnReason) {
        boolean update = orderReturnReasonService.updateById(orderReturnReason);
        return toOperationResult(update);
    }

    /**
     * 批量删除退货原因
     *
     * @param ids 退货原因id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:orderreturnreason:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = orderReturnReasonService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}