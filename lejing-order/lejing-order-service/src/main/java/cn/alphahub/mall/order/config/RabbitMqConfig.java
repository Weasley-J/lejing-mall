package cn.alphahub.mall.order.config;

import cn.alphahub.mall.order.domain.MqMessage;
import cn.alphahub.mall.order.service.MqMessageService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.util.Date;

/**
 * RabbitMQ配置类
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    @Resource
    private CachingConnectionFactory connectionFactory;
    @Resource
    private MqMessageService mqMessageService;

    /**
     * <b>给Spring IOC容器中初始化一个RabbitTemplate</b>
     *
     * @return RabbitTemplate实例
     */
    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return customizeRabbit(rabbitTemplate);
    }

    /**
     * <b>把发送的消息存储为Json字符串</b>
     * <ul>
     *     <li>消费者可直接序列化为对象，对象必须实现Serializable接口</li>
     * </ul>
     *
     * @return Jackson2Json的消息转换器实例Bean
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * <b>定制RabbitTemplate</b>
     * <p>
     * <ul>
     *     <li>1. 服务收到消息就会回调:<br><code>spring.rabbitmq.publisher-confirm-type=correlated</code></li>
     *     <li>2. 设置确认回调(消息正确抵达队列就会进行回调):<br><code>spring.rabbitmq.publisher-returns=true<br>spring.rabbitmq.template.mandatory=true</code></li>
     *     <li>3. 设置确认回调ReturnsCallback</li>
     *     <li>4. 消费端确认(保证每个消息都被正确消费，此时才可以broker删除这个消息)</li>
     * </ul>
     */
    private RabbitTemplate customizeRabbit(RabbitTemplate rabbitTemplate) {
        /**
         *
         * 设置生产者消息publish-confirm确认回调函数
         * 1: 只要消息抵达Broker就ack=true
         * correlationData: 当前消息的唯一关联数据(这个是消息的唯一id)
         * ack: 消息是否成功收到
         * cause: 失败的原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息确认相关数据:{} ==> 是否确认（ack）:{} ==> 原因: {}", JSONUtil.toJsonStr(correlationData), ack, cause);
            if (null != correlationData) {
                MqMessage mqMessage = new MqMessage();
                mqMessage.setMessageId(correlationData.getId());
                mqMessage.setUpdateTime(new Date());
                mqMessage.setMessageStatus(!ack ? 2 : 3);
                mqMessageService.updateById(mqMessage);
            }
        });
        /**
         * 只要消息没有投递给指定的队列，就触发这个失败回调
         * message: 投递失败的消息详细信息
         * replyCode: 回复的状态码
         * replyText: 回复的文本内容
         * exchange: 当时这个消息发给哪个交换机
         * routingKey: 当时这个消息用哪个路由键
         */
        rabbitTemplate.setReturnsCallback(returned -> {
            Message returnedMessage = returned.getMessage();
            log.info("RabbitMQ失败回调信息:[{}]", JSONUtil.toJsonStr(returnedMessage));
        });
        return rabbitTemplate;
    }

}
