package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import cn.alphahub.mall.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 订单Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Slf4j
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 根据订单号查询订单状态
     *
     * @param orderSn 订单号
     * @return 订单
     */
    @GetMapping(value = "/status")
    public Result<Order> getOrderStatus(@RequestParam("orderSn") String orderSn) {
        log.info("根据订单号查询订单状态,订单号:{}", orderSn);
        Order order = orderService.getOne(new QueryWrapper<Order>().lambda().eq(Order::getOrderSn, orderSn).last(" limit 1"));
        log.info("根据订单号查询订单状态: {}", JSONUtil.toJsonStr(order));
        return Result.of(order);
    }

    /**
     * 获取当前登录用的订单数据
     * <ul>
     *     <li>用户信息从拦截器里面取</li>
     * </ul>
     *
     * @param page 分页数据
     * @return 当前登录用户的订单数据
     */
    @PostMapping("/member/order/list")
    public Result<PageResult<OrderVo>> getMemberOrderList(@RequestBody PageDomain page) {
        PageResult<OrderVo> pageResult = orderService.getMemberOrderList(page);
        return Result.of(pageResult);
    }

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
    @GetMapping("/list")
    public Result<PageResult<Order>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Order order
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Order> pageResult = orderService.queryPage(pageDomain, order);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取订单详情
     *
     * @param id 订单主键id
     * @return 订单详细信息
     */
    @GetMapping("/info/{id}")
    public Result<Order> info(@PathVariable("id") Long id) {
        Order order = orderService.getById(id);
        return ObjectUtils.anyNotNull(order) ? Result.of(order) : Result.fail();
    }

    /**
     * 新增订单
     *
     * @param order 订单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Order order) {
        boolean save = orderService.save(order);
        return Result.of(save);
    }

    /**
     * 修改订单
     *
     * @param order 订单, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Order order) {
        boolean update = orderService.updateById(order);
        return Result.of(update);
    }

    /**
     * 批量删除订单
     *
     * @param ids 订单id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = orderService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
