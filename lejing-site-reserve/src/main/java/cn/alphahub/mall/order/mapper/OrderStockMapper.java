package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.OrderStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单库存表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Mapper
public interface OrderStockMapper extends BaseMapper<OrderStock> {

}
