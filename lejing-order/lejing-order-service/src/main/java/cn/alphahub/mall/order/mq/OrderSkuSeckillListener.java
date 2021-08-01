package cn.alphahub.mall.order.mq;

import cn.alphahub.common.mq.SeckillOrderTo;
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
import static cn.alphahub.common.constant.MqConstant.SECKILL_ORDER_QUEUE;
import static cn.alphahub.common.constant.MqConstant.SECKILL_ORDER_ROUTING_KEY;

/**
 * 商品秒杀MQ监听队列
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/01
 */
@Slf4j
@Component
public class OrderSkuSeckillListener {

    @Resource
    private OrderService orderService;

    /**
     * 处理商品秒杀订单
     *
     * @param deliveryTag   消息投递标签
     * @param correlationId 消息关联id
     * @param message       消息
     * @param channel       通道
     * @param seckillOrder  秒杀商品订单数据
     */
    @RabbitListeners(value = {@RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = SECKILL_ORDER_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
            exchange = @Exchange(name = ORDER_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {SECKILL_ORDER_ROUTING_KEY}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                          @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
                          Message message, Channel channel, SeckillOrderTo seckillOrder
    ) throws IOException {
        log.info("准备创建秒杀订单：{}", JSONUtil.toJsonStr(seckillOrder));
        try {
            orderService.createSeckillOrder(seckillOrder);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
