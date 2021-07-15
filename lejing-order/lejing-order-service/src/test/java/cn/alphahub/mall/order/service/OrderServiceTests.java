package cn.alphahub.mall.order.service;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrderServiceTests {
    @Resource
    OrderService orderService;
    @Resource
    OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        System.err.println("-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-------------------------");
    }

    /**
     * 补全订单项订单id
     */
    @Test
    void fillOrderIdForItem() {
        List<OrderItem> orderItems = orderItemService.list(new QueryWrapper<OrderItem>().lambda()
                .select(OrderItem::getId, OrderItem::getOrderId, OrderItem::getOrderSn)
        );
        List<OrderItem> _orderItems = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            Order order = orderService.getOne(new QueryWrapper<Order>().lambda()
                    .select(Order::getId)
                    .eq(Order::getOrderSn, orderItem.getOrderSn())
                    .last(" LIMIT 1"));
            if (order != null) {
                Long orderId = order.getId();
                orderItem.setOrderId(orderId);
                _orderItems.add(orderItem);
            }
        });
        orderItemService.updateBatchById(_orderItems);
    }
}
