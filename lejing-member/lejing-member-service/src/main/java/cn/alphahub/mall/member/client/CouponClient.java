package cn.alphahub.mall.member.client;

import cn.alphahub.mall.coupon.api.CouponApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 优惠券信息FeignClient
 *
 * @author Weasley J
 * @date 2021-02-06 02:47:18
 */
@FeignClient(name = "lejing-coupon")
public interface CouponClient extends CouponApi {

}
