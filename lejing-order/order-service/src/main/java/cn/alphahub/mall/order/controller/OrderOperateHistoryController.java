package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.OrderOperateHistory;
import cn.alphahub.mall.order.service.OrderOperateHistoryService;

import java.util.Arrays;

/**
 * 订单操作历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:43:32
 */
@RestController
@RequestMapping("order/orderoperatehistory")
public class OrderOperateHistoryController extends BaseController {
    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    /**
     * 查询订单操作历史记录列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param orderOperateHistory 订单操作历史记录,字段选择性传入,默认等值查询
     * @return 订单操作历史记录分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:orderoperatehistory:list")
    public BaseResult<PageResult<OrderOperateHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderOperateHistory orderOperateHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderOperateHistory> pageResult = orderOperateHistoryService.queryPage(pageDomain, orderOperateHistory);
        return (BaseResult<PageResult<OrderOperateHistory>>) toPageableResult(pageResult);
    }

    /**
     * 获取订单操作历史记录详情
     *
     * @param id 订单操作历史记录主键id
     * @return 订单操作历史记录详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:orderoperatehistory:info")
    public BaseResult<OrderOperateHistory> info(@PathVariable("id") Long id){
        OrderOperateHistory orderOperateHistory = orderOperateHistoryService.getById(id);
        return (BaseResult<OrderOperateHistory>) toResponseResult(orderOperateHistory);
    }

    /**
     * 新增订单操作历史记录
     *
     * @param orderOperateHistory 订单操作历史记录元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:orderoperatehistory:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ OrderOperateHistory orderOperateHistory) {
        boolean save = orderOperateHistoryService.save(orderOperateHistory);
        return toOperationResult(save);
    }

    /**
     * 修改订单操作历史记录
     *
     * @param orderOperateHistory 订单操作历史记录,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ OrderOperateHistory orderOperateHistory) {
        boolean update = orderOperateHistoryService.updateById(orderOperateHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单操作历史记录
     *
     * @param ids 订单操作历史记录id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:orderoperatehistory:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = orderOperateHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
