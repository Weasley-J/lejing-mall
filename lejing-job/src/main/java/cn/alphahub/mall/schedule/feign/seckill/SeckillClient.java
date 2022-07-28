package cn.alphahub.mall.schedule.feign.seckill;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.seckill.api.SeckillApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 秒杀商品feign顶层接口 - rpc调用客服端
 *
 * @author lwj
 * @version 1.0
 * @date 2021-08-30 19:40
 */
@FeignClient(name = AppConstant.SECKILL_SERVICE, contextId = "seckillClient", path = "")
public interface SeckillClient extends SeckillApi {

}
