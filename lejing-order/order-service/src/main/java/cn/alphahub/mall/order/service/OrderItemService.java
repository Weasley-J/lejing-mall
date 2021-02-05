package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单项信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:43:31
 */
public interface OrderItemService extends IService<OrderItem>, PageService<OrderItem> {

}