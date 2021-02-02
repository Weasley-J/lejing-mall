package cn.alphahub.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:22:38
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

