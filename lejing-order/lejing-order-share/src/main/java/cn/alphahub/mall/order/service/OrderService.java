package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.cart.vo.CartItemVo;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.dto.vo.OrderSubmitVo;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import cn.alphahub.mall.order.dto.vo.PayAsyncVo;
import cn.alphahub.mall.order.dto.vo.PayVo;
import cn.alphahub.mall.order.dto.vo.SubmitOrderResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderService extends IService<Order> {

    /**
     * 查询订单分页列表
     *
     * @param pageDomain 分页数据
     * @param order      分页对象
     * @return 订单分页数据
     */
    PageResult<Order> queryPage(PageDomain pageDomain, Order order);

    /**
     * 订单结算确认页
     *
     * @return 订单确认页需要用的数据
     */
    OrderConfirmVo confirmOrder();

    /**
     * 查询购物项内容列表
     *
     * @return 购物项内容列表
     */
    List<CartItemVo> getUserCartItems();

    /**
     * 提交订单结算 - 下单功能
     *
     * @param submitVo 订单提交数据
     * @return 提交订单响应数据
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    /**
     * 关闭订单
     *
     * @param order 订单数据
     */
    void closeOrder(Order order);

    /**
     * 获取当前订单的支付信息（构建支付数据）
     *
     * @param orderSn 订单号
     * @return 支付宝数据
     */
    PayVo getOrderPaymentInfo(String orderSn);

    /**
     * 获取当前登录用的订单数据
     * <ul>
     *     <li>用户信息从拦截器里面取</li>
     * </ul>
     *
     * @param page 分页数据
     * @return 当前登录用户的订单数据
     */
    PageResult<OrderVo> getMemberOrderList(PageDomain page);

    /**
     * 根据支付结果修改订单状态
     *
     * @param asyncVo 付款异步回调数据
     * @return success
     */
    String handlePaidResult(PayAsyncVo asyncVo);
}
