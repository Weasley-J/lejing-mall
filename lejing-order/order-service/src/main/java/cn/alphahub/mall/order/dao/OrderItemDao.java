package cn.alphahub.mall.order.dao;

import cn.alphahub.mall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {

}
