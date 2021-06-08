package cn.alphahub.mall.order.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import org.springframework.web.bind.annotation.*;

/**
 * 退货原因-feign远程调用api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RequestMapping("order/orderreturnreason")
public interface OrderReturnReasonApi {

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
    BaseResult<PageResult<OrderReturnReason>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderReturnReason orderReturnReason
    );

    /**
     * 获取退货原因详情
     *
     * @param id 退货原因主键id
     * @return 退货原因详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<OrderReturnReason> info(@PathVariable("id") Long id);

    /**
     * 新增退货原因
     *
     * @param orderReturnReason 退货原因元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody OrderReturnReason orderReturnReason);

    /**
     * 修改退货原因
     *
     * @param orderReturnReason 退货原因, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody OrderReturnReason orderReturnReason);

    /**
     * 批量删除退货原因
     *
     * @param ids 退货原因id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
