package cn.alphahub.mall.ware.controller.app;

import cn.alphahub.common.constant.MqConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.ware.feign.OrderClient;
import cn.alphahub.mall.ware.feign.SkuInfoClient;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 测试Rabbit - Controller
 *
 * @author lwj
 */
@Slf4j
@RestController
@RequestMapping("/app")
public class HelloRabbitController {

    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private OrderClient orderClient;
    @Resource
    private SkuInfoClient skuInfoClient;

    @GetMapping("/mq/send/msg")
    public BaseResult<Void> send() {
        Order order = new Order();
        order.setId(Long.parseLong("10010"));
        order.setNote("测试Order-10010");
        order.setCreateTime(new Date());
        // 给MQ发消息
        amqpTemplate.convertAndSend(MqConstant.STOCK_EVENT_EXCHANGE, MqConstant.STOCK_ROUTING_KEY_STOCK_LOCKED, order, message -> {
            message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
            return message;
        });
        return BaseResult.ok();
    }

    @GetMapping("/status")
    public BaseResult<Order> getOrderStatus(@RequestParam("orderSn") String orderSn) {
        return orderClient.getOrderStatus(orderSn);
    }

    @GetMapping("/skuInfo")
    public BaseResult<SkuInfo> skuInfo() {
        return skuInfoClient.info(1L);
    }
}
