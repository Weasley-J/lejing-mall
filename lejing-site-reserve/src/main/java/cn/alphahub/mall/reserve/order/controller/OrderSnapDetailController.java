package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.order.domain.OrderSnapDetail;
import cn.alphahub.mall.reserve.order.service.OrderSnapDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 订单快照表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@RestController
@RequestMapping("site/order/ordersnapdetail")
public class OrderSnapDetailController extends BaseController {
    @Autowired
    private OrderSnapDetailService orderSnapDetailService;

    /**
     * 查询订单快照表列表
     *
     * @param page            当前页码,默认第1页
     * @param rows            显示行数,默认10条
     * @param orderColumn     排序排序字段,默认不排序
     * @param isAsc           排序方式,desc或者asc
     * @param orderSnapDetail 订单快照表, 查询字段选择性传入, 默认为等值查询
     * @return 订单快照表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<OrderSnapDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderSnapDetail orderSnapDetail
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderSnapDetail> pageResult = orderSnapDetailService.queryPage(pageDomain, orderSnapDetail);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单快照表详情
     *
     * @param orderId 订单快照表主键id
     * @return 订单快照表详细信息
     */
    @GetMapping("/info/{orderId}")
    public BaseResult<OrderSnapDetail> info(@PathVariable("orderId") Long orderId) {
        OrderSnapDetail orderSnapDetail = orderSnapDetailService.getById(orderId);
        return ObjectUtils.anyNotNull(orderSnapDetail) ? BaseResult.ok(orderSnapDetail) : BaseResult.fail();
    }

    /**
     * 新增订单快照表
     *
     * @param orderSnapDetail 订单快照表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody OrderSnapDetail orderSnapDetail) {
        boolean save = orderSnapDetailService.save(orderSnapDetail);
        return toOperationResult(save);
    }

    /**
     * 修改订单快照表
     *
     * @param orderSnapDetail 订单快照表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody OrderSnapDetail orderSnapDetail) {
        boolean update = orderSnapDetailService.updateById(orderSnapDetail);
        return toOperationResult(update);
    }

    /**
     * 批量删除订单快照表
     *
     * @param orderIds 订单快照表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{orderIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] orderIds) {
        boolean delete = orderSnapDetailService.removeByIds(Arrays.asList(orderIds));
        return toOperationResult(delete);
    }
}
