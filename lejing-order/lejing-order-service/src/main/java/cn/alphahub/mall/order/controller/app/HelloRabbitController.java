package cn.alphahub.mall.order.controller.app;

import cn.alphahub.common.constant.MqConstant;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 测试Rabbit - Controller
 *
 * @author lwj
 */
@Slf4j
@Controller
public class HelloRabbitController {

    @Resource
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/{page}.html")
    public String listPage(@PathVariable("page") String page) {
        return page;
    }

    /**
     * 送消息给MQ
     *
     * @return ok
     */
    @ResponseBody
    @GetMapping("sendMsgToMq")
    public Result<String> sendMsgToMq() {
        for (int i = 1; i <= 10; i++) {
            log.warn("第 [{}] 次循环", i);
            if (i % 2 == 0) {
                Order order = new Order();
                order.setId(Long.parseLong("10086000" + i));
                order.setNote("测试Order_00" + i);
                order.setCreateTime(new Date());
                amqpTemplate.convertAndSend(MqConstant.ORDER_ITEM_EXCHANGE, MqConstant.ORDER_ITEM_ROUTING_KEY, order, message -> {
                    message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
                    return message;
                });
            } else {
                OrderReturnReason reason = new OrderReturnReason();
                reason.setId(Long.parseLong("10086000" + i));
                reason.setName("测试OrderReturnReason_00" + i);
                reason.setCreateTime(new Date());
                amqpTemplate.convertAndSend(MqConstant.ORDER_ITEM_EXCHANGE, MqConstant.ORDER_ITEM_ROUTING_KEY, reason, message -> {
                    message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
                    return message;
                });
            }
        }
        return Result.ok();
    }

    /**
     * 测试创建订单发送消息给 ORDER_EVENT_RELEASE_ORDER_QUEUE 队列
     */
    @ResponseBody
    @GetMapping("/order/order/create/test")
    public Result<Void> createOrderTest() {
        Order order = new Order();
        order.setId(Long.parseLong("10086"));
        order.setNote("测试Order_10086");
        order.setCreateTime(new Date());
        // 给MQ发消息
        amqpTemplate.convertAndSend(MqConstant.ORDER_EVENT_EXCHANGE, MqConstant.ORDER_ROUTING_KEY_CREATE_ORDER, order, message -> {
            message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
            return message;
        });
        return Result.ok();

    }

    @ResponseBody
    @GetMapping("/order/order/release/test")
    public Result<Void> releaseOrderTest() {
        Order order = new Order();
        order.setId(Long.parseLong("10010"));
        order.setNote("测试Order-10010");
        order.setCreateTime(new Date());
        // 给MQ发消息
        amqpTemplate.convertAndSend(MqConstant.ORDER_EVENT_EXCHANGE, MqConstant.ORDER_ROUTING_KEY_RELEASE_ORDER, order, message -> {
            message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
            return message;
        });
        return Result.ok();
    }
}
