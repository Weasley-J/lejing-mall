package cn.alphahub.mall.auth.controller;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enumeration.CheckCodeStatus;
import cn.alphahub.mall.auth.domain.UserLogin;
import cn.alphahub.mall.auth.domain.UserRegister;
import cn.alphahub.mall.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 登录页Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/17
 */
@Slf4j
@Controller
public class LoginController extends BaseController {
    @Resource
    private AuthService authService;

    /**
     * 发送验证码给用户手机
     *
     * @param phone  接收验证码的手机号
     * @param origin 请求来源: 1-使用移动端请求验证码,2-使用浏览器请求验证码,0-未知来源,默认: 0;验证码长度: 移动端4位,浏览器6位, origin为空验证码长度6位;
     * @return 验证码发送结果提示
     */
    @ResponseBody
    @GetMapping("/sms/sendCode")
    public BaseResult<String> sendCheckCode(
            @RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "origin", required = false, defaultValue = "0") Integer origin
    ) {
        CheckCodeStatus status = authService.sendCheckCode(phone, origin);
        if (Objects.equals(status.getValue(), CheckCodeStatus.SUCCESS.getValue())) {
            return BaseResult.success(status.getName());
        }
        return BaseResult.success(status.getValue(), status.getName());
    }

    /**
     * 用户注册
     *
     * @param userRegister       注册提交的用户信息
     * @param redirectAttributes 模拟重定向携带数据,重定向也可以保留数据，不会丢失
     * @return 登录页|注册页面
     */
    @PostMapping("/register")
    public String register(@Valid UserRegister userRegister, BindingResult result, RedirectAttributes redirectAttributes) {
        return authService.register(userRegister, result, redirectAttributes);
    }

    /**
     * 用户登录
     *
     * @param userLogin 用户信息
     * @return 首页
     */
    @PostMapping("/login")
    public String login(UserLogin userLogin, RedirectAttributes redirectAttributes) {
        return authService.login(userLogin, redirectAttributes);
    }
}
