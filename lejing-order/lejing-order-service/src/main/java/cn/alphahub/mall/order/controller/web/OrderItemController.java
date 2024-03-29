package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.service.OrderItemService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 订单项信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RestController
@RequestMapping("order/orderitem")
public class OrderItemController {
    @Resource
    private OrderItemService orderItemService;

    /**
     * 查询订单项信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param orderItem   订单项信息, 查询字段选择性传入, 默认为等值查询
     * @return 订单项信息分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<OrderItem>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderItem orderItem
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderItem> pageResult = orderItemService.queryPage(pageDomain, orderItem);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单项信息详情
     *
     * @param id 订单项信息主键id
     * @return 订单项信息详细信息
     */
    @GetMapping("/info/{id}")
    public Result<OrderItem> info(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.getById(id);
        return ObjectUtils.anyNotNull(orderItem) ? Result.of(orderItem) : Result.fail();
    }

    /**
     * 新增订单项信息
     *
     * @param orderItem 订单项信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderItem orderItem) {
        boolean save = orderItemService.save(orderItem);
        return Result.of(save);
    }

    /**
     * 修改订单项信息
     *
     * @param orderItem 订单项信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody OrderItem orderItem) {
        boolean update = orderItemService.updateById(orderItem);
        return Result.of(update);
    }

    /**
     * 批量删除订单项信息
     *
     * @param ids 订单项信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = orderItemService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
