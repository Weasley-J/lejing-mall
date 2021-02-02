package cn.alphahub.mall.order.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.order.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

