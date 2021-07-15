package cn.alphahub.mall.order.listener;

import cn.alphahub.common.constant.MqConstant;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class OrderItemListenerTest {

    @Resource
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    void setUp() {
        log.info("-------------------------");
    }

    @AfterEach
    void tearDown() {
        log.info("-------------------------");
    }

    @Test
    void receiveMessage() {
        for (int i = 0; i < 10; i++) {
            OrderItem item = new OrderItem();
            item.setId(Long.parseLong((i + 1 + "")));
            item.setOrderSn("哈哈哈哈哈");
            item.setSpuName("哈哈哈哈哈");
            item.setSpuPic("哈哈哈哈哈");
            item.setSpuBrand("哈哈哈哈哈");
            item.setSkuName("哈哈哈哈哈");
            item.setSkuPic("哈哈哈哈哈");
            item.setSkuAttrsVals("哈哈哈哈哈");
            amqpTemplate.convertAndSend(MqConstant.ORDER_ITEM_ROUTING_KEY, item);
            log.info("发送消息:{}", JSONUtil.toJsonStr(item));
        }
    }
}
