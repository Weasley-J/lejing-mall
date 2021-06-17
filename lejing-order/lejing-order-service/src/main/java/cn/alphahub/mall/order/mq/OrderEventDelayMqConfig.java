package cn.alphahub.mall.order.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.alphahub.common.constant.MqConstant.ORDER_EVENT_DELAY_QUEUE;
import static cn.alphahub.common.constant.MqConstant.ORDER_EVENT_EXCHANGE;
import static cn.alphahub.common.constant.MqConstant.ORDER_ROUTING_KEY_CREATE_ORDER;
import static cn.alphahub.common.constant.MqConstant.X_DEAD_LETTER_ARGS;

/**
 * 订单事件RabbitMQ延时队列配置
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
public class OrderEventDelayMqConfig {

    /**
     * 绑定延时队列和topic交换机
     *
     * @return 延时队列和交换机的绑定关系
     */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayTopicExchange())
                .with(ORDER_ROUTING_KEY_CREATE_ORDER);
    }

    /**
     * @return 延时队列
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(ORDER_EVENT_DELAY_QUEUE)
                .withArguments(X_DEAD_LETTER_ARGS)
                .build();
    }

    /**
     * @return topic类型的交换机
     */
    @Bean
    public TopicExchange delayTopicExchange() {
        return new TopicExchange(ORDER_EVENT_EXCHANGE, true, false);
    }
}
