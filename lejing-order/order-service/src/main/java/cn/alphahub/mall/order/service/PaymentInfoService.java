package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.order.domain.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 支付信息表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
public interface PaymentInfoService extends IService<PaymentInfo>, PageService<PaymentInfo> {

}