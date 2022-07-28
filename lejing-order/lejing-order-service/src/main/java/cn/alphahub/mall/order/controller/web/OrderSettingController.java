package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.order.domain.OrderSetting;
import cn.alphahub.mall.order.service.OrderSettingService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 订单配置信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController {
    @Resource
    private OrderSettingService orderSettingService;

    /**
     * 查询订单配置信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param orderSetting 订单配置信息, 查询字段选择性传入, 默认为等值查询
     * @return 订单配置信息分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<OrderSetting>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderSetting orderSetting
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderSetting> pageResult = orderSettingService.queryPage(pageDomain, orderSetting);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单配置信息详情
     *
     * @param id 订单配置信息主键id
     * @return 订单配置信息详细信息
     */
    @GetMapping("/info/{id}")
    public Result<OrderSetting> info(@PathVariable("id") Long id) {
        OrderSetting orderSetting = orderSettingService.getById(id);
        return ObjectUtils.anyNotNull(orderSetting) ? Result.ok(orderSetting) : Result.fail();
    }

    /**
     * 新增订单配置信息
     *
     * @param orderSetting 订单配置信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderSetting orderSetting) {
        boolean save = orderSettingService.save(orderSetting);
        return Result.ok(save);
    }

    /**
     * 修改订单配置信息
     *
     * @param orderSetting 订单配置信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody OrderSetting orderSetting) {
        boolean update = orderSettingService.updateById(orderSetting);
        return Result.ok(update);
    }

    /**
     * 批量删除订单配置信息
     *
     * @param ids 订单配置信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = orderSettingService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
