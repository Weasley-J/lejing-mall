package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.coupon.domain.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
public interface CouponService extends IService<Coupon>, PageService<Coupon> {

}