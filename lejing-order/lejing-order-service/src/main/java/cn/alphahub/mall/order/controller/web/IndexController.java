package cn.alphahub.mall.order.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/11
 */
@Controller
public class IndexController {

    /**
     * 首页
     *
     * @return 首页
     */
    @GetMapping("/")
    public String index() {
        return "../static/index.html";
    }
}
