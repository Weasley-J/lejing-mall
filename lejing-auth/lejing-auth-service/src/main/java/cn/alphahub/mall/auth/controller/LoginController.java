package cn.alphahub.mall.auth.controller;

import cn.alphahub.common.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <b>登录页Controller</b>
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/03/17
 */
@Controller
public class LoginController extends BaseController {

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/reg.html")
    public String register() {
        return "reg";
    }
}
