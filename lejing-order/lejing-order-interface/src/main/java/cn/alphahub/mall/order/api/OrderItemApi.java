package cn.alphahub.mall.order.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderItem;
import org.springframework.web.bind.annotation.*;

/**
 * 订单项信息-feign远程调用api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderItemApi {
    /**
     * 查询订单项信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param orderItem   订单项信息, 查询字段选择性传入, 默认为等值查询
     * @return 订单项信息分页数据
     */
    @GetMapping("order/orderitem/list")
    BaseResult<PageResult<OrderItem>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            OrderItem orderItem
    );

    /**
     * 获取订单项信息详情
     *
     * @param id 订单项信息主键id
     * @return 订单项信息详细信息
     */
    @GetMapping("order/orderitem/info/{id}")
    BaseResult<OrderItem> info(@PathVariable("id") Long id);

    /**
     * 新增订单项信息
     *
     * @param orderItem 订单项信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("order/orderitem/save")
    BaseResult<Boolean> save(@RequestBody OrderItem orderItem);

    /**
     * 修改订单项信息
     *
     * @param orderItem 订单项信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("order/orderitem/update")
    BaseResult<Boolean> update(@RequestBody OrderItem orderItem);

    /**
     * 批量删除订单项信息
     *
     * @param ids 订单项信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("order/orderitem/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
