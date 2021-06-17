package cn.alphahub.mall.order.mq;

import cn.alphahub.mall.order.domain.Order;
import cn.hutool.core.date.DateUtil;
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
public class OrderEventListener {

    @RabbitListeners(value = {@RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = ORDER_EVENT_RELEASE_ORDER_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
            exchange = @Exchange(name = ORDER_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {ORDER_ROUTING_KEY_RELEASE_ORDER}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Message message, Channel channel, Order order) {
        log.info("处理释放订单事件，MQ数据：{}, 载荷：{}", JSONUtil.toJsonStr(new String(message.getBody())), JSONUtil.toJsonStr(order));
        try {
            channel.basicAck(deliveryTag, false);
            System.err.println("处理释放订单事件: 签收了[ " + deliveryTag + " ]号消息, " + DateUtil.now());
        } catch (IOException e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
        }
    }
}
