package cn.alphahub.mall.product.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.seckill.api.SeckillApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 秒杀商品feign客户端
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021年7月29日23:32:22
 */
@FeignClient(name = AppConstant.SECKILL_SERVICE)
public interface SeckillClient extends SeckillApi {

}
