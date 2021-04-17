package cn.alphahub.mall.order.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 把发送的消息存储为Json字符串
     * <ul>
     *     <li>消费者可直接序列化为对象，对象必须实现Serializable接口</li>
     * </ul>
     *
     * @return Jackson2Json的消息转换器Bean
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
