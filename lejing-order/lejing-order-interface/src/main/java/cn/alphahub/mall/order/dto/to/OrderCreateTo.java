package cn.alphahub.mall.order.dto.to;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单
     */
    private Order order;

    /**
     * 订单项信息
     */
    private List<OrderItem> orderItems;

    /**
     * 订单计算的应付价格
     **/
    private BigDecimal payPrice;

    /**
     * 运费
     **/
    private BigDecimal fare;

}
