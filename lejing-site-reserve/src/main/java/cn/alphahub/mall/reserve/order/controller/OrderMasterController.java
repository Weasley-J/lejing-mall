package cn.alphahub.mall.reserve.order.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
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
public class OrderMasterController extends BaseController {
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
    public BaseResult<PageResult<OrderMaster>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderMaster orderMaster
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderMaster> pageResult = orderMasterService.queryPage(pageDomain, orderMaster);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取主订单表详情
     *
     * @param masterOrderId 主订单表主键id
     * @return 主订单表详细信息
     */
    @GetMapping("/info/{masterOrderId}")
    public BaseResult<OrderMaster> info(@PathVariable("masterOrderId") Long masterOrderId) {
        OrderMaster orderMaster = orderMasterService.getById(masterOrderId);
        return ObjectUtils.anyNotNull(orderMaster) ? BaseResult.ok(orderMaster) : BaseResult.fail();
    }

    /**
     * 新增主订单表
     *
     * @param orderMaster 主订单表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody OrderMaster orderMaster) {
        boolean save = orderMasterService.save(orderMaster);
        return toOperationResult(save);
    }

    /**
     * 修改主订单表
     *
     * @param orderMaster 主订单表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody OrderMaster orderMaster) {
        boolean update = orderMasterService.updateById(orderMaster);
        return toOperationResult(update);
    }

    /**
     * 批量删除主订单表
     *
     * @param masterOrderIds 主订单表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{masterOrderIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] masterOrderIds) {
        boolean delete = orderMasterService.removeByIds(Arrays.asList(masterOrderIds));
        return toOperationResult(delete);
    }
}
