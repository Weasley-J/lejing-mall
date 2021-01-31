package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.coupon.entity.SpuBoundsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:27:55
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

