package cn.alphahub.mall.order.listener;

import cn.alphahub.common.mq.RabbitConstant;
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
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <b>订单列表监听器</b>
 * <p>
 *     <ul><code>@RabbitListener</code>可标注在类、方法上，<code>@RabbitHandler</code>标注在方法上</ul>
 *     <ul>
 *         <li>1. 当<code>@RabbitListener</code>标注在类上面可以使用<code>@RabbitHandler</code>标注在不同的方法上，通过方法重载的方式处理消息</li>
 *         <li>2. 也可以根据单一职责将<code>@RabbitListener</code>标注在不同业务类的业务方法上进行业务处理</li>
 *     </ul>
 * </p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitConstant.ORDER_ITEM_QUEUE, durable = "true"),
        exchange = @Exchange(value = RabbitConstant.ORDER_ITEM_EXCHANGE, type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
        key = {RabbitConstant.ORDER_ITEM_ROUTING_KEY})
)
public class OrderItemListener {

    /**
     * 接收订单消息（测试）
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  生产者发送的元数据实体类（Spring自动分装，前提：domain必须实现序列换接口）
     * @param channel 传输数据的通道，收发消息的通道
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConstant.ORDER_ITEM_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitConstant.ORDER_ITEM_EXCHANGE, type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {RabbitConstant.ORDER_ITEM_ROUTING_KEY + ".test"})
    )
    public void receiveMessage(Message message, Channel channel, OrderItem domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("传输数据的通道:{}", channel);
        log.info("消息体:{}", new String(body));
        log.info("消息头:{}", JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));
    }

    /**
     * <b>处理订单数据</b>
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  订单数据
     */
    @RabbitHandler
    public void receiveMessage(Message message, Order domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("消息体:{}", new String(body));
        log.info("消息头:{}", JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));
    }

    /**
     * <b>处理订单退款</b>
     *
     * @param message 收到的消息（消息头、消息体）
     * @param domain  退货原因元数据
     */
    @RabbitHandler
    public void receiveMessage(Message message, OrderReturnReason domain) {
        log.info("接受订单正向流事件:{}", message);
        // 消息体
        byte[] body = message.getBody();
        // 消息头
        MessageProperties prop = message.getMessageProperties();
        log.info("消息体:{}", new String(body));
        log.info("消息头:{}", JSONUtil.toJsonPrettyStr(prop));
        log.info("实体类元数据:{}", JSONUtil.toJsonPrettyStr(domain));
    }
}
