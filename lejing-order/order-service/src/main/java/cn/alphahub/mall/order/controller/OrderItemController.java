package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.service.OrderItemService;

import java.util.Arrays;

/**
 * 订单项信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@RestController
@RequestMapping("order/orderitem")
public class OrderItemController extends BaseController {
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 查询订单项信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param orderItem 订单项信息,字段选择性传入,默认等值查询
     * @return 订单项信息分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:orderitem:list")
    public BaseResult<PageResult<OrderItem>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderItem orderItem
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderItem> pageResult = orderItemService.queryPage(pageDomain, orderItem);
        return (BaseResult<PageResult<OrderItem>>) toPageableResult(pageResult);
    }

    /**
     * 获取订单项信息详情
     *
     * @param id 订单项信息主键id
     * @return 订单项信息详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<OrderItem> info(@PathVariable("id") Long id){
        OrderItem orderItem = orderItemService.getById(id);
        return (BaseResult<OrderItem>) toResponseResult(orderItem);
    }

    /**
     * 新增订单项信息
     *
     * @param orderItem 订单项信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:orderitem:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ OrderItem orderItem) {
        boolean save = orderItemService.save(orderItem);
        return toOperationResult(save);
    }

    /**
     * 修改订单项信息
     *
     * @param orderItem 订单项信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ OrderItem orderItem) {
        boolean update = orderItemService.updateById(orderItem);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单项信息
     *
     * @param ids 订单项信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:orderitem:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = orderItemService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}