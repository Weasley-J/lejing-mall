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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
import java.util.List;
import java.util.Objects;

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
    private WareOrderTaskService wareOrderTaskService;
    @Resource
    private WareOrderTaskDetailService wareOrderTaskDetailService;
    @Resource
    private OrderClient orderClient;
    @Resource
    private WareSkuService wareSkuService;


    @RabbitListeners(value = {@RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = STOCK_EVENT_RELEASE_QUEUE, durable = Exchange.TRUE, exclusive = Exchange.FALSE, autoDelete = Exchange.FALSE),
                    exchange = @Exchange(name = STOCK_EVENT_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = {STOCK_ROUTING_KEY_STOCK_RELEASE}))
    })
    public void onMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Header(AmqpHeaders.CORRELATION_ID) String correlationId, Message message, Channel channel, StockLockedTo data) {
        try {
            log.info("处理解锁库存事件，MQ correlationId：{}, 载荷：{}, message：{}", correlationId, JsonUtil.toJsonStr(data), new String(message.getBody()));
            StockDetailTo detail = data.getDetailTo();
            //查询“库存工作单”判断
            List<WareOrderTaskDetail> list = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetail>().lambda()
                    .select(WareOrderTaskDetail::getSkuId)
                    .eq(WareOrderTaskDetail::getId, detail.getId())
                    .last(" limit 1")
            );
            // 无需解锁库存
            if (ObjectUtils.isEmpty(list)) {
                log.info("库存工作单不存在，无需解锁库存.");
                channel.basicAck(deliveryTag, false);
            } else {
                /*
                 * 解锁库存:
                 * (1). 订单数据不存在,必须解锁;
                 * (2). 订单状态:已取消,解锁库存; 订单状态:没取消,不能解锁库存;
                 */
                WareOrderTask wareOrderTask = wareOrderTaskService.getById(data.getId());
                BaseResult<Order> orderStatus = orderClient.getOrderStatus(wareOrderTask.getOrderSn());
                log.info("远程根据订单号查询订单状态:{}", JsonUtil.toJsonStr(orderStatus));
                if (orderStatus.getSuccess()) {
                    Order order = orderStatus.getData();
                    // 订单已取消
                    if (null == order || Objects.equals(order.getStatus(), OrderConstant.OrderStatusEnum.CANCELLED.getValue())) {
                        log.info("订单已取消，解锁库存.");
                        wareSkuService.unlockStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum());
                        channel.basicAck(deliveryTag, false);
                    } else {
                        // 其他订单状态, msg拒绝，重回队列
                        log.info("远程根据订单号查询订单状态失败，拒绝处理，重回队列.");
                        channel.basicReject(deliveryTag, true);
                    }
                } else {
                    throw new BizException("查询订单[" + wareOrderTask.getOrderSn() + "]状态失败");
                }
            }
        } catch (IOException e) {
            log.error("消费者签收消息错误:{}", e.getLocalizedMessage(), e);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ioException) {
                log.error("拒绝签收消息错误:{}", e.getLocalizedMessage(), e);
            }
        }
    }
}
