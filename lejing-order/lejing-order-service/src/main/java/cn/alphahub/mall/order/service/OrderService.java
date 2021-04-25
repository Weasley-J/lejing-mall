package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 去结算确认页
     */
    OrderConfirmVo confirmOrder();
}
