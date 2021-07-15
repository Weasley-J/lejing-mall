package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.OrderStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单库存表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
public interface OrderStockService extends IService<OrderStock>, PageService<OrderStock> {

}