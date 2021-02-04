package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.OrderOperateHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单操作历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistory>, PageService<OrderOperateHistory> {

}