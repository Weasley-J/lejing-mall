package cn.alphahub.mall.seckill.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.mq.SeckillOrderTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello Controller
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/07/07
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * 你好
     *
     * @return 你好
     */
    @GetMapping("/test")
    public String hello() {
        return "hello";
    }

    /**
     * 秒杀
     *
     * @param seckillOrderTo 秒杀
     * @return 秒杀
     */
    @GetMapping("SeckillOrderTo")
    public BaseResult<SeckillOrderTo> seckillOrder(@RequestBody SeckillOrderTo seckillOrderTo) {
        return BaseResult.ok();
    }
}
