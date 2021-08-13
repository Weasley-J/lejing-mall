package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.request.OrderDetailReq;
import cn.alphahub.mall.order.dto.response.OrderDetailResp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询订单数据
     *
     * @param req 查询参数
     * @return 订单详情
     */
    List<OrderDetailResp> selectOrderDetail(@Param("req") OrderDetailReq req);
}
