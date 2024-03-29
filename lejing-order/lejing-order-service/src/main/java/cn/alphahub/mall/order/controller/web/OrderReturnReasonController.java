package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.alphahub.mall.order.service.OrderReturnReasonService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 退货原因Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RestController
@RequestMapping("order/orderreturnreason")
public class OrderReturnReasonController {
    @Resource
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 查询退货原因列表
     *
     * @param page              当前页码,默认第1页
     * @param rows              显示行数,默认10条
     * @param orderColumn       排序排序字段,默认不排序
     * @param isAsc             排序方式,desc或者asc
     * @param orderReturnReason 退货原因, 查询字段选择性传入, 默认为等值查询
     * @return 退货原因分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<OrderReturnReason>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderReturnReason orderReturnReason
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<OrderReturnReason> pageResult = orderReturnReasonService.queryPage(pageDomain, orderReturnReason);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取退货原因详情
     *
     * @param id 退货原因主键id
     * @return 退货原因详细信息
     */
    @GetMapping("/info/{id}")
    public Result<OrderReturnReason> info(@PathVariable("id") Long id) {
        OrderReturnReason orderReturnReason = orderReturnReasonService.getById(id);
        return ObjectUtils.anyNotNull(orderReturnReason) ? Result.of(orderReturnReason) : Result.fail();
    }

    /**
     * 新增退货原因
     *
     * @param orderReturnReason 退货原因元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderReturnReason orderReturnReason) {
        boolean save = orderReturnReasonService.save(orderReturnReason);
        return Result.of(save);
    }

    /**
     * 修改退货原因
     *
     * @param orderReturnReason 退货原因, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody OrderReturnReason orderReturnReason) {
        boolean update = orderReturnReasonService.updateById(orderReturnReason);
        return Result.of(update);
    }

    /**
     * 批量删除退货原因
     *
     * @param ids 退货原因id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = orderReturnReasonService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
