package cn.alphahub.mall.seckill.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.coupon.api.SeckillSessionApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 促销系统feign客户端
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
@FeignClient(name = AppConstant.COUPON_SERVICE)
public interface SeckillSessionClient extends SeckillSessionApi {

}
