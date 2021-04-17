package cn.alphahub.common.mq;

/**
 * <b>RabbitMQ常量类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
public class RabbitConstant {
    /**
     * 订单列表队列名称
     */
    public static final String ORDER_ITEM_QUEUE = "LEJING.ORDER.QUEUE";
    /**
     * 订单列表交换机名称
     */
    public static final String ORDER_ITEM_EXCHANGE = "LEJING.ORDER.EXCHANGE";
    /**
     * 订单列表收发消息的路由键名称
     */
    public static final String ORDER_ITEM_ROUTING_KEY = "lejing.order.item";
}
