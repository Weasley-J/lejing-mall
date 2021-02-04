package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.OrderReturnReason;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Mapper
public interface OrderReturnReasonMapper extends BaseMapper<OrderReturnReason> {
	
}