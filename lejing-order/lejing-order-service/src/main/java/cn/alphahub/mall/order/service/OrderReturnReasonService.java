package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 退货原因Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
public interface OrderReturnReasonService extends IService<OrderReturnReason>, PageService<OrderReturnReason> {

}