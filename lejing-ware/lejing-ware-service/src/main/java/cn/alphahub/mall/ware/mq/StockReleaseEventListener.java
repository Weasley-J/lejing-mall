package cn.alphahub.mall.ware.mq;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.mq.StockDetailTo;
import cn.alphahub.common.mq.StockLockedTo;
import cn.alphahub.common.util.JsonUtil;
import cn.alphahub.mall.order.constant.OrderConstant;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.alphahub.mall.ware.feign.OrderClient;
import cn.alphahub.mall.ware.service.WareOrderTaskDetailService;
import cn.alphahub.mall.ware.service.WareOrderTaskService;
import cn.alphahub.mall.ware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Objects;

import static cn.alphahub.common.constant.MqConstant.ORDER_ROUTING_KEY_RELEASE_OTHERS;
import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_EXCHANGE;
import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_RELEASE_QUEUE;
import static cn.alphahub.common.constant.MqConstant.STOCK_ROUTING_KEY_STOCK_RELEASE;

/**
 * 普通队列监听库存事件
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/16
 */
@Slf4j
@Component
public class StockReleaseEventListener {

    @Resource
    private OrderClient orderClient;
    @Resource
    private WareSkuService wareSkuService;
    @Resource
    private WareOrderTaskService wareOrderTaskService;
    @Resource
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    /**
     * 处理解锁库存事件
     */
    @RabbitListeners(value = {@RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = STOCK_EVENT_RELEASE_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
            exchange = @Exchange(name = STOCK_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {STOCK_ROUTING_KEY_STOCK_RELEASE}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                          @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
                          Message message, Channel channel, StockLockedTo data
    ) {
        log.info("处理解锁库存事件，correlationId：{}, 载荷：{}, message：{}", correlationId, JsonUtil.toJsonStr(data), new String(message.getBody()));
        try {
            StockDetailTo detail = data.getDetailTo();
            if (ObjectUtils.isNotEmpty(detail)) {
                // 查询“库存工作单”是否存在，如果库存工作单不存在无需解锁库存
                WareOrderTaskDetail wareOrderTaskDetail = wareOrderTaskDetailService.getById(detail.getId());
                if (ObjectUtils.isNotEmpty(wareOrderTaskDetail)) {
                    /**
                     * 解锁库存:
                     *     (1). 订单数据不存在,必须解锁;
                     *     (2). 订单状态:已取消,解锁库存; 订单状态:没取消,不能解锁库存;
                     */
                    WareOrderTask wareOrderTask = wareOrderTaskService.getById(data.getId());
                    BaseResult<Order> orderStatus = orderClient.getOrderStatus(wareOrderTask.getOrderSn());
                    log.info("远程根据订单号查询订单状态:{}", JsonUtil.toJsonStr(orderStatus));
                    if (orderStatus.getSuccess()) {
                        Order order = orderStatus.getData();
                        // 订单已取消
                        if (null == order || Objects.equals(order.getStatus(), OrderConstant.OrderStatusEnum.CANCELLED.getValue())) {
                            log.info("订单已取消，解锁库存.");
                            // 1 = 已锁定 -> 才能解锁释放库存
                            if (Objects.equals(detail.getLockStatus(), 1)) {
                                wareSkuService.unlockStock(detail.getSkuId(), detail.getWareId(), detail.getId(), detail.getSkuNum());
                            }
                            channel.basicAck(deliveryTag, false);
                        }
                    } else {
                        throw new BizException("查询订单[" + wareOrderTask.getOrderSn() + "]状态失败");
                    }
                }
            }
        } catch (Exception ex1) {
            log.error("消费者签收消息错误:{}, correlationId: {}", ex1.getLocalizedMessage(), correlationId, ex1);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex2) {
                log.error("拒绝签收消息错误:{}", ex2.getLocalizedMessage(), ex2);
            }
        }
    }

    /**
     * <b>库存服务处理关闭订单事件</b>
     * <p>
     * 防止订单库存服务卡顿,导致订单消息一直不能修改, 库存消息优先到期,
     * 查询的订单状态一直新建状态, 订单状态卡顿, 库存得不到解锁.
     * </p>
     */
    @RabbitListeners(value = {@RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = STOCK_EVENT_RELEASE_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
            exchange = @Exchange(name = STOCK_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {ORDER_ROUTING_KEY_RELEASE_OTHERS}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                          @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
                          Message message, Channel channel, Order order
    ) {
        log.info("库存服务收到处理关闭订单消息，correlationId：{}, message：{}", correlationId, new String(message.getBody()));
        try {
            if (StringUtils.isBlank(order.getOrderSn())) {
                channel.basicNack(deliveryTag, false, true);
            } else {
                wareSkuService.unlockStock(order);
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception ex1) {
            log.error("消费者签收消息错误:{}, correlationId: {}", ex1.getLocalizedMessage(), correlationId, ex1);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (Exception ex2) {
                log.error("拒绝签收消息错误:{}", ex2.getLocalizedMessage(), ex2);
            }
        }
    }

}
