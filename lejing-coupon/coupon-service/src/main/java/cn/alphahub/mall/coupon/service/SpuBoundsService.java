package cn.alphahub.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.coupon.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:22:38
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

