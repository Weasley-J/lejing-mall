package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.OrderSetting;
import cn.alphahub.mall.order.service.OrderSettingService;

import java.util.Arrays;

/**
 * 订单配置信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController extends BaseController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 查询订单配置信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param orderSetting 订单配置信息,字段选择性传入,默认等值查询
     * @return 订单配置信息分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:ordersetting:list")
    public BaseResult<PageResult<OrderSetting>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderSetting orderSetting
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderSetting> pageResult = orderSettingService.queryPage(pageDomain, orderSetting);
        return (BaseResult<PageResult<OrderSetting>>) toPageableResult(pageResult);
    }

    /**
     * 获取订单配置信息详情
     *
     * @param id 订单配置信息主键id
     * @return 订单配置信息详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<OrderSetting> info(@PathVariable("id") Long id){
        OrderSetting orderSetting = orderSettingService.getById(id);
        return (BaseResult<OrderSetting>) toResponseResult(orderSetting);
    }

    /**
     * 新增订单配置信息
     *
     * @param orderSetting 订单配置信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:ordersetting:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ OrderSetting orderSetting) {
        boolean save = orderSettingService.save(orderSetting);
        return toOperationResult(save);
    }

    /**
     * 修改订单配置信息
     *
     * @param orderSetting 订单配置信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ OrderSetting orderSetting) {
        boolean update = orderSettingService.updateById(orderSetting);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单配置信息
     *
     * @param ids 订单配置信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:ordersetting:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = orderSettingService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}