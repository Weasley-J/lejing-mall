package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.order.domain.OrderSiteCoupon;
import cn.alphahub.mall.reserve.order.service.OrderSiteCouponService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地预约入场券卷号表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@RestController
@RequestMapping("site/order/ordersitecoupon")
public class OrderSiteCouponController extends BaseController {
    @Autowired
    private OrderSiteCouponService orderSiteCouponService;

    /**
     * 查询场地预约入场券卷号表列表
     *
     * @param page            当前页码,默认第1页
     * @param rows            显示行数,默认10条
     * @param orderColumn     排序排序字段,默认不排序
     * @param isAsc           排序方式,desc或者asc
     * @param orderSiteCoupon 场地预约入场券卷号表, 查询字段选择性传入, 默认为等值查询
     * @return 场地预约入场券卷号表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<OrderSiteCoupon>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderSiteCoupon orderSiteCoupon
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderSiteCoupon> pageResult = orderSiteCouponService.queryPage(pageDomain, orderSiteCoupon);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地预约入场券卷号表详情
     *
     * @param orderMasterId 场地预约入场券卷号表主键id
     * @return 场地预约入场券卷号表详细信息
     */
    @GetMapping("/info/{orderMasterId}")
    public BaseResult<OrderSiteCoupon> info(@PathVariable("orderMasterId") Long orderMasterId) {
        OrderSiteCoupon orderSiteCoupon = orderSiteCouponService.getById(orderMasterId);
        return ObjectUtils.anyNotNull(orderSiteCoupon) ? BaseResult.ok(orderSiteCoupon) : BaseResult.fail();
    }

    /**
     * 新增场地预约入场券卷号表
     *
     * @param orderSiteCoupon 场地预约入场券卷号表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody OrderSiteCoupon orderSiteCoupon) {
        boolean save = orderSiteCouponService.save(orderSiteCoupon);
        return toOperationResult(save);
    }

    /**
     * 修改场地预约入场券卷号表
     *
     * @param orderSiteCoupon 场地预约入场券卷号表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody OrderSiteCoupon orderSiteCoupon) {
        boolean update = orderSiteCouponService.updateById(orderSiteCoupon);
        return toOperationResult(update);
    }

    /**
     * 批量删除场地预约入场券卷号表
     *
     * @param orderMasterIds 场地预约入场券卷号表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{orderMasterIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] orderMasterIds) {
        boolean delete = orderSiteCouponService.removeByIds(Arrays.asList(orderMasterIds));
        return toOperationResult(delete);
    }
}
