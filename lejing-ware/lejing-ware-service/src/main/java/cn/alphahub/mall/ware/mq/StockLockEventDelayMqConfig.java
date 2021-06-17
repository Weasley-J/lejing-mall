package cn.alphahub.mall.ware.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.alphahub.common.constant.MqConstant.STOCK_DEAD_LETTER_ROUTING_KEY;
import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_DELAY_QUEUE;
import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_EXCHANGE;
import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_X_MESSAGE_TTL;
import static cn.alphahub.common.constant.MqConstant.STOCK_ROUTING_KEY_STOCK_LOCKED;

/**
 * 库存锁定 - RabbitMQ延时队列配置
 * <p></p>
 * <ul>
 *     <li>延时队列和交换机的绑定</li>
 *     <li>延时队列</li>
 *     <li>topic类型的交换机</li>
 * </ul>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/16
 */
@Configuration
public class StockLockEventDelayMqConfig {

    /**
     * <p>绑定延时队列和topic交换机</p>
     * <b>Binding routing key: stock.locked</b>
     *
     * @return 延时队列和交换机的绑定关系
     */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayTopicExchange())
                .with(STOCK_ROUTING_KEY_STOCK_LOCKED);
    }

    /**
     * Queue: STOCK.DELAY.QUEUE
     *
     * @return 延时队列
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(STOCK_EVENT_DELAY_QUEUE)
                .ttl(STOCK_EVENT_X_MESSAGE_TTL)
                .deadLetterExchange(STOCK_EVENT_EXCHANGE)
                .deadLetterRoutingKey(STOCK_DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    /**
     * TopicExchange: STOCK.EVENT.EXCHANGE
     *
     * @return topic类型的交换机
     */
    @Bean
    public TopicExchange delayTopicExchange() {
        return new TopicExchange(STOCK_EVENT_EXCHANGE, true, false);
    }
}
