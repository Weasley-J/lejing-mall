package cn.alphahub.mall.seckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/test")
    public String hello() {
        int i = 1 / 0;
        return "hello";
    }
}
