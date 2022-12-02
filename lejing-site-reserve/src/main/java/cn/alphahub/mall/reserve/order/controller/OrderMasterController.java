package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.order.domain.OrderMaster;
import cn.alphahub.mall.reserve.order.service.OrderMasterService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 主订单表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@RestController
@RequestMapping("site/order/ordermaster")
public class OrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 查询主订单表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param orderMaster 主订单表, 查询字段选择性传入, 默认为等值查询
     * @return 主订单表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<OrderMaster>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderMaster orderMaster
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderMaster> pageResult = orderMasterService.queryPage(pageDomain, orderMaster);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取主订单表详情
     *
     * @param masterOrderId 主订单表主键id
     * @return 主订单表详细信息
     */
    @GetMapping("/info/{masterOrderId}")
    public Result<OrderMaster> info(@PathVariable("masterOrderId") Long masterOrderId) {
        OrderMaster orderMaster = orderMasterService.getById(masterOrderId);
        return ObjectUtils.anyNotNull(orderMaster) ? Result.of(orderMaster) : Result.fail();
    }

    /**
     * 新增主订单表
     *
     * @param orderMaster 主订单表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderMaster orderMaster) {
        boolean save = orderMasterService.save(orderMaster);
        return Result.of(save);
    }

    /**
     * 修改主订单表
     *
     * @param orderMaster 主订单表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody OrderMaster orderMaster) {
        boolean update = orderMasterService.updateById(orderMaster);
        return Result.of(update);
    }

    /**
     * 批量删除主订单表
     *
     * @param masterOrderIds 主订单表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{masterOrderIds}")
    public Result<Boolean> delete(@PathVariable Long[] masterOrderIds) {
        boolean delete = orderMasterService.removeByIds(Arrays.asList(masterOrderIds));
        return Result.of(delete);
    }
}
