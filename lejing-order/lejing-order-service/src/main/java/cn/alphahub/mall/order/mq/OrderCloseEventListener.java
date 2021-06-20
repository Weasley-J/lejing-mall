package cn.alphahub.mall.order.mq;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

import static cn.alphahub.common.constant.MqConstant.ORDER_EVENT_EXCHANGE;
import static cn.alphahub.common.constant.MqConstant.ORDER_EVENT_RELEASE_ORDER_QUEUE;
import static cn.alphahub.common.constant.MqConstant.ORDER_ROUTING_KEY_RELEASE_ORDER;

/**
 * 释放订单事件监听器
 *
 * @author lwj
 * @version 1.0
 * @date 2021/06/13
 */
@Slf4j
@Component
public class OrderCloseEventListener {
    @Resource
    private OrderService orderService;

    /**
     * <p>处理定时关闭订单</p>
     *
     * @param deliveryTag   消息投递标签
     * @param correlationId 消息关联id
     * @param message       消息
     * @param channel       通道
     * @param order         订单数据
     */
    @RabbitListeners(value = {@RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = ORDER_EVENT_RELEASE_ORDER_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
            exchange = @Exchange(name = ORDER_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {ORDER_ROUTING_KEY_RELEASE_ORDER}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                          @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
                          Message message, Channel channel, Order order
    ) throws IOException {
        log.info("订单服务处理关闭订单事件，correlationId: {}, 载荷：{}, MQ数据：{}", correlationId, JSONUtil.toJsonStr(order), JSONUtil.toJsonStr(new String(message.getBody())));
        try {
            orderService.closeOrder(order);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
