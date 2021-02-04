package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券领取历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
public interface CouponHistoryService extends IService<CouponHistory>, PageService<CouponHistory> {

}