package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 主订单表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
public interface OrderMasterService extends IService<OrderMaster>, PageService<OrderMaster> {

}