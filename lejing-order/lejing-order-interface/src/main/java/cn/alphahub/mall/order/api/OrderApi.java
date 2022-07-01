package cn.alphahub.mall.order.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单-feign远程调用api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderApi {

    /**
     * 根据订单号查询订单状态
     *
     * @param orderSn 订单号
     * @return 订单状态
     */
    @GetMapping(value = "order/order/status")
    Result<Order> getOrderStatus(@RequestParam("orderSn") String orderSn);

    /**
     * 获取当前登录用的订单数据
     *
     * @param page 分页数据
     * @return 当前登录用户的订单数据
     */
    @PostMapping("order/order/member/order/list")
    Result<PageResult<OrderVo>> getMemberOrderList(@RequestBody PageDomain page);

    /**
     * 查询订单列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param order       订单, 查询字段选择性传入, 默认为等值查询
     * @return 订单分页数据
     */
    @GetMapping("order/order/list")
    Result<PageResult<Order>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Order order
    );

    /**
     * 获取订单详情
     *
     * @param id 订单主键id
     * @return 订单详细信息
     */
    @GetMapping("order/order/info/{id}")
    Result<Order> info(@PathVariable("id") Long id);

    /**
     * 新增订单
     *
     * @param order 订单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("order/order/save")
    Result<Boolean> save(@RequestBody Order order);

    /**
     * 修改订单
     *
     * @param order 订单, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("order/order/update")
    Result<Boolean> update(@RequestBody Order order);

    /**
     * 批量删除订单
     *
     * @param ids 订单id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("order/order/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
