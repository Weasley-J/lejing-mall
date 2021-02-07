package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {
	
}