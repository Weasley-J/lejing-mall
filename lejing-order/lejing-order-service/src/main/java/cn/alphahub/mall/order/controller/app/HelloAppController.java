package cn.alphahub.mall.order.controller.app;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * 测试 - Controller
 *
 * @author lwj
 */
@Controller
public class HelloAppController {

    @Resource
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/{page}.html")
    public String listPage(@PathVariable("page") String page) {
        return page;
    }
}

