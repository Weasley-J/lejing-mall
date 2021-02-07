package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.service.OrderService;

import java.util.Arrays;

/**
 * 订单Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@RestController
@RequestMapping("order/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param order 订单,字段选择性传入,默认为等值查询
     * @return 订单分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:order:list")
    public BaseResult<PageResult<Order>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Order order
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Order> pageResult = orderService.queryPage(pageDomain, order);
        return (BaseResult<PageResult<Order>>) toPageableResult(pageResult);
    }

    /**
     * 获取订单详情
     *
     * @param id 订单主键id
     * @return 订单详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:order:info")
    public BaseResult<Order> info(@PathVariable("id") Long id){
        Order order = orderService.getById(id);
        return (BaseResult<Order>) toResponseResult(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:order:save")
    public BaseResult<Boolean> save(@RequestBody Order order) {
        boolean save = orderService.save(order);
        return toOperationResult(save);
    }

    /**
     * 修改订单
     *
     * @param order 订单,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:order:update")
    public BaseResult<Boolean> update(@RequestBody Order order) {
        boolean update = orderService.updateById(order);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单
     *
     * @param ids 订单id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:order:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = orderService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
