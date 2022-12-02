package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.order.domain.OrderReimburse;
import cn.alphahub.mall.reserve.order.service.OrderReimburseService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 订单退款表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@RestController
@RequestMapping("site/order/orderreimburse")
public class OrderReimburseController {
    @Autowired
    private OrderReimburseService orderReimburseService;

    /**
     * 查询订单退款表列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param orderReimburse 订单退款表, 查询字段选择性传入, 默认为等值查询
     * @return 订单退款表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<OrderReimburse>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderReimburse orderReimburse
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderReimburse> pageResult = orderReimburseService.queryPage(pageDomain, orderReimburse);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单退款表详情
     *
     * @param reimburseId 订单退款表主键id
     * @return 订单退款表详细信息
     */
    @GetMapping("/info/{reimburseId}")
    public Result<OrderReimburse> info(@PathVariable("reimburseId") Long reimburseId) {
        OrderReimburse orderReimburse = orderReimburseService.getById(reimburseId);
        return ObjectUtils.anyNotNull(orderReimburse) ? Result.of(orderReimburse) : Result.fail();
    }

    /**
     * 新增订单退款表
     *
     * @param orderReimburse 订单退款表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderReimburse orderReimburse) {
        boolean save = orderReimburseService.save(orderReimburse);
        return Result.of(save);
    }

    /**
     * 修改订单退款表
     *
     * @param orderReimburse 订单退款表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody OrderReimburse orderReimburse) {
        boolean update = orderReimburseService.updateById(orderReimburse);
        return Result.of(update);
    }

    /**
     * 批量删除订单退款表
     *
     * @param reimburseIds 订单退款表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{reimburseIds}")
    public Result<Boolean> delete(@PathVariable Long[] reimburseIds) {
        boolean delete = orderReimburseService.removeByIds(Arrays.asList(reimburseIds));
        return Result.of(delete);
    }
}
