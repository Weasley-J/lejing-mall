package cn.alphahub.mall.order.dto.response;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情数据 - 响应数据
 *
 * @author liuwening
 * @version 1.0
 * @date 2021/08/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDetailResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单
     */
    private Order order;
    /**
     * 订单项信息
     */
    private List<OrderItem> orderItems;
}
