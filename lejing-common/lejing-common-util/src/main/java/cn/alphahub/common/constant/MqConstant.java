package cn.alphahub.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <b>RabbitMQ常量类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/17
 */
public class MqConstant {
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

    // --------------------------------------------------------------------

    /**
     * 订单事件 - 交换机
     */
    public static final String ORDER_EVENT_EXCHANGE = "ORDER.EVENT.EXCHANGE";
    /**
     * 订单事件 - 延时队列
     */
    public static final String ORDER_EVENT_DELAY_QUEUE = "ORDER.DELAY.QUEUE";
    /**
     * 订单事件 - 释放队列
     */
    public static final String ORDER_EVENT_RELEASE_ORDER_QUEUE = "ORDER.RELEASE.QUEUE";
    /**
     * 订单事件 - 延时队列路由键
     */
    public static final String ORDER_ROUTING_KEY_CREATE_ORDER = "order.create.order";
    /**
     * 订单事件普通队列 - 释放订单路由键
     */
    public static final String ORDER_ROUTING_KEY_RELEASE_ORDER = "order.release.order";
    /**
     * 订单事件：释放订单和释放库存路由键
     */
    public static final String ORDER_ROUTING_KEY_RELEASE_OTHERS = "order.release.other.#";
    public static final String ORDER_ROUTING_KEY_RELEASE_OTHER = "order.release.other";

    // 订单事件 - 延时队列（死信交换机、死信路由键、消息存活时间,延时队列参数）
    /**
     * 消息过期时间（消息存活时间：默认单位毫秒，1s = 1000ms） - 1分钟
     */
    public static final Integer ORDER_EVENT_X_MESSAGE_TTL = 1 * 60 * 1000;
    /**
     * The key for the arguments to apply when declaring this queue
     */
    public static final Map<String, Object> X_DEAD_LETTER_ARGS = new LinkedHashMap<>(3);
    /**
     * 库存事件交换机
     */
    public static final String STOCK_EVENT_EXCHANGE = "STOCK.EVENT.EXCHANGE";
    // --------------------------------------------------------------------
    /**
     * 库存事件延时队列
     */
    public static final String STOCK_EVENT_DELAY_QUEUE = "STOCK.DELAY.QUEUE";
    /**
     * 库存事件延时队列路由键
     */
    public static final String STOCK_ROUTING_KEY_STOCK_LOCKED = "stock.locked";
    /**
     * 库存事件死信路由键
     */
    public static final String STOCK_DEAD_LETTER_ROUTING_KEY = "stock.release";
    /**
     * 库存事件释放队列
     */
    public static final String STOCK_EVENT_RELEASE_QUEUE = "STOCK.RELEASE.QUEUE";
    /**
     * 库存事件水释放队列路由键
     */
    public static final String STOCK_ROUTING_KEY_STOCK_RELEASE = "stock.release.#";
    /**
     * Set the message time-to-live after which it will be discarded, or routed to the dead-letter-exchange
     * <ul>
     *     <li>消息存活时间（消息存活时间：默认单位毫秒，1s = 1000ms）</li>
     * </ul>
     */
    public static final Integer STOCK_EVENT_X_MESSAGE_TTL = 2 * 60 * 1000;

    // ------------------------------------------------------------------------------
    /**
     * 商品秒杀路由键
     */
    public static final String SECKILL_ORDER_ROUTING_KEY = "order.seckill.order";
    /**
     * 商品秒杀队列
     */
    public static final String SECKILL_ORDER_QUEUE = "ORDER.SECKILL.QUEUE";


    static {
        X_DEAD_LETTER_ARGS.put("x-dead-letter-exchange", ORDER_EVENT_EXCHANGE);
        X_DEAD_LETTER_ARGS.put("x-dead-letter-routing-key", ORDER_ROUTING_KEY_RELEASE_ORDER);
        X_DEAD_LETTER_ARGS.put("x-message-ttl", ORDER_EVENT_X_MESSAGE_TTL);
    }
}
