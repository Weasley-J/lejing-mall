package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
	
}