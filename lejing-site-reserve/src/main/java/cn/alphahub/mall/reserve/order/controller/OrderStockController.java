package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.order.domain.OrderStock;
import cn.alphahub.mall.reserve.order.service.OrderStockService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 订单库存表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@RestController
@RequestMapping("site/order/orderstock")
public class OrderStockController extends BaseController {
    @Autowired
    private OrderStockService orderStockService;

    /**
     * 查询订单库存表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param orderStock  订单库存表, 查询字段选择性传入, 默认为等值查询
     * @return 订单库存表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<OrderStock>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderStock orderStock
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderStock> pageResult = orderStockService.queryPage(pageDomain, orderStock);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单库存表详情
     *
     * @param stockId 订单库存表主键id
     * @return 订单库存表详细信息
     */
    @GetMapping("/info/{stockId}")
    public BaseResult<OrderStock> info(@PathVariable("stockId") Long stockId) {
        OrderStock orderStock = orderStockService.getById(stockId);
        return ObjectUtils.anyNotNull(orderStock) ? BaseResult.ok(orderStock) : BaseResult.fail();
    }

    /**
     * 新增订单库存表
     *
     * @param orderStock 订单库存表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody OrderStock orderStock) {
        boolean save = orderStockService.save(orderStock);
        return toOperationResult(save);
    }

    /**
     * 修改订单库存表
     *
     * @param orderStock 订单库存表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody OrderStock orderStock) {
        boolean update = orderStockService.updateById(orderStock);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单库存表
     *
     * @param stockIds 订单库存表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{stockIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] stockIds) {
        boolean delete = orderStockService.removeByIds(Arrays.asList(stockIds));
        return toOperationResult(delete);
    }
}
