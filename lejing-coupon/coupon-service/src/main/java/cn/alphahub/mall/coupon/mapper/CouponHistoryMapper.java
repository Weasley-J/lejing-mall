package cn.alphahub.mall.coupon.mapper;

import cn.alphahub.mall.coupon.domain.CouponHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Mapper
public interface CouponHistoryMapper extends BaseMapper<CouponHistory> {
	
}