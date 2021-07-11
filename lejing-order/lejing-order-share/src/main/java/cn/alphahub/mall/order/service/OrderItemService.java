package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单项信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderItemService extends IService<OrderItem> {

    /**
     * 查询订单项信息分页列表
     *
     * @param pageDomain 分页数据
     * @param orderItem  分页对象
     * @return 订单项信息分页数据
     */
    PageResult<OrderItem> queryPage(PageDomain pageDomain, OrderItem orderItem);

}
