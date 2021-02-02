package cn.alphahub.mall.order.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 退货原因
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

