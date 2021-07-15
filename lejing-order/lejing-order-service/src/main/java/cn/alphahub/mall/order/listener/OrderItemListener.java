package cn.alphahub.mall.order.listener;

import cn.alphahub.common.constant.MqConstant;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <b>订单列表监听器</b>
 * <p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
@Slf4j
@Component
public class OrderItemListener {

    /**
     * 接收订单消息（测试）
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  生产者发送的元数据实体类（Spring自动分装，前提：domain必须实现序列换接口）
     * @param channel 传输数据的通道，收发消息的通道
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.ORDER_ITEM_QUEUE, durable = Exchange.TRUE),
            exchange = @Exchange(value = MqConstant.ORDER_ITEM_EXCHANGE, type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = Exchange.TRUE),
            key = {MqConstant.ORDER_ITEM_ROUTING_KEY}))
    public void receiveMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Message message, Channel channel, OrderItem domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("传输数据的通道:{}", channel);
        log.info("消息体:{}", new String(body));
        log.info("correlationId:{}, 消息头:{}", prop.getCorrelationId(), JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));

        // deliveryTag在channel内按顺序自增
        try {
            if (deliveryTag % 2 == 0) {
                // 消费者处理完消息，确认ACK，签收消息（非批量签收）
                channel.basicAck(deliveryTag, false);
                System.out.println("签收了[" + deliveryTag + "]号消息");
            } else {
                // 退收消息，requeue=false 丢弃，requeue=true 发回服务器从新入队，
                // true if the rejected message(s) should be requeued rather than discarded/dead-lettered
                channel.basicNack(deliveryTag, false, true);
                System.out.println("未签收[" + deliveryTag + "]号消息");
            }
        } catch (IOException e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * <b>处理订单数据</b>
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  订单数据
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.ORDER_ITEM_QUEUE, durable = Exchange.TRUE),
            exchange = @Exchange(value = MqConstant.ORDER_ITEM_EXCHANGE, type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {MqConstant.ORDER_ITEM_ROUTING_KEY}))
    public void receiveMessage(Message message, Channel channel, Order domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("消息体:{}", new String(body));
        log.info("correlationId:[{}], 消息头:{}", prop.getCorrelationId(), JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));

        // deliveryTag在channel内按顺序自增
        long deliveryTag = prop.getDeliveryTag();
        try {
            if (deliveryTag % 2 == 0) {
                // 消费者处理完消息，确认ACK，签收消息（非批量签收）
                channel.basicAck(deliveryTag, false);
                System.out.println("签收了[" + deliveryTag + "]号消息");
            } else {
                // 退收消息，requeue=false 丢弃，requeue=true 发回服务器从新入队，
                // true if the rejected message(s) should be requeued rather than discarded/dead-lettered
                channel.basicNack(deliveryTag, false, true);
                System.out.println("未签收[" + deliveryTag + "]号消息");
            }
        } catch (IOException e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * <b>处理订单退款</b>
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  退货原因元数据
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.ORDER_ITEM_QUEUE, durable = Exchange.TRUE),
            exchange = @Exchange(value = MqConstant.ORDER_ITEM_EXCHANGE, type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {MqConstant.ORDER_ITEM_ROUTING_KEY}))
    public void receiveMessage(Message message, Channel channel, OrderReturnReason domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("消息体:{}", new String(body));
        log.info("correlationId:[{}], 消息头:{}", prop.getCorrelationId(), JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));

        // deliveryTag在channel内按顺序自增
        long deliveryTag = prop.getDeliveryTag();
        try {
            if (deliveryTag % 2 == 0) {
                // 消费者处理完消息，确认ACK，签收消息（非批量签收）
                channel.basicAck(deliveryTag, false);
                System.out.println("签收了[" + deliveryTag + "]号消息");
            } else {
                // 退收消息，requeue=false 丢弃，requeue=true 发回服务器从新入队，
                // true if the rejected message(s) should be requeued rather than discarded/dead-lettered
                channel.basicNack(deliveryTag, false, true);
                System.out.println("未签收[" + deliveryTag + "]号消息");
            }
        } catch (IOException e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
        }
    }
}
