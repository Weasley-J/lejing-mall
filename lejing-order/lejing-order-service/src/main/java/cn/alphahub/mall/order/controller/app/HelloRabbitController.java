package cn.alphahub.mall.order.controller.app;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.mq.RabbitConstant;
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
    public BaseResult<String> sendMsgToMq() {
        for (int i = 1; i <= 10; i++) {
            log.warn("第 [{}] 次循环", i);
            if (i % 2 == 0) {
                Order order = new Order();
                order.setId(Long.parseLong("10086000" + i));
                order.setNote("测试Order_00" + i);
                order.setCreateTime(new Date());
                amqpTemplate.convertAndSend(RabbitConstant.ORDER_ITEM_EXCHANGE, RabbitConstant.ORDER_ITEM_ROUTING_KEY, order, message -> {
                    message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
                    return message;
                });
            } else {
                OrderReturnReason reason = new OrderReturnReason();
                reason.setId(Long.parseLong("10086000" + i));
                reason.setName("测试OrderReturnReason_00" + i);
                reason.setCreateTime(new Date());
                amqpTemplate.convertAndSend(RabbitConstant.ORDER_ITEM_EXCHANGE, RabbitConstant.ORDER_ITEM_ROUTING_KEY, reason, message -> {
                    message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
                    return message;
                });
            }
        }
        return BaseResult.ok();
    }
}
