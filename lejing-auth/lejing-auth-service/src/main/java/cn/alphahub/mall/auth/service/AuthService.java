package cn.alphahub.mall.auth.service;

import cn.alphahub.common.enums.CheckCodeStatus;
import cn.alphahub.mall.auth.domain.UserLogin;
import cn.alphahub.mall.auth.domain.UserRegister;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 授权服务顶层接口
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
public interface AuthService {

    /**
     * <b>发送验证码</b>
     * <p>使用RabbitMQ发送短信，使用Redis做短信防刷</p>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 验证码发送结果枚举提示
     */
    CheckCodeStatus sendCheckCode(String phone, Integer origin);

    /**
     * 用户注册
     *
     * @param userRegister       注册提交的用户信息
     * @param redirectAttributes 模拟重定向携带数据,重定向也可以保留数据，不会丢失
     * @param result             验证错误信息
     * @return 登录页|注册页面
     */
    String register(UserRegister userRegister, BindingResult result, RedirectAttributes redirectAttributes);

    /**
     * 用户登录
     *
     * @param userLogin          用户信息
     * @param redirectAttributes 模拟重定向携带数据,重定向也可以保留数据，不会丢失
     * @param session            session
     * @return 首页
     */
    String login(UserLogin userLogin, RedirectAttributes redirectAttributes, HttpSession session);
}
