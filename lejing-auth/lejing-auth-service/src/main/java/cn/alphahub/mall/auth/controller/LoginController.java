package cn.alphahub.mall.auth.controller;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enumeration.CheckCodeStatus;
import cn.alphahub.mall.auth.domain.UserRegister;
import cn.alphahub.mall.auth.feign.MemberClient;
import cn.alphahub.mall.auth.service.AuthService;
import cn.alphahub.mall.auth.util.CodecUtils;
import cn.alphahub.mall.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MemberClient memberClient;

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
        if (result.hasErrors()) {
            Map<Object, Object> map = result.getFieldErrors().stream().collect(
                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
            );
            redirectAttributes.addFlashAttribute("errors", map);
            // 校验错误转发到注册页面
            return "redirect:http://auth.lejing.com/reg.html";
        }
        // 校验验证码
        String phone = userRegister.getPhone();
        String checkCode = userRegister.getCode();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = AuthConstant.REDIS_KEY_PREFIX + phone;
        String redisCacheCode = ops.get(key);
        if (StringUtils.isNotBlank(redisCacheCode)) {
            redisCacheCode = redisCacheCode.split(",")[0];
            //验证码校验成功
            if (StringUtils.equals(checkCode, redisCacheCode)) {

                // 删除验证码
                stringRedisTemplate.delete(key);

                // 密码加密存储
                String password = userRegister.getPassword();
                String encodePassword = CodecUtils.encodePassword(password);

                // 远程调用会员服务完后完成注册
                Member member = Member.builder()
                        .username(userRegister.getUserName())
                        .password(encodePassword)
                        .mobile(userRegister.getPhone())
                        .build();
                BaseResult<Boolean> save = memberClient.save(member);
                String message = save.getMessage();
                Integer repCode = save.getCode();
                log.info("响应状态码：" + repCode + "；响应消息：" + message);
                // 保存用户信息成功
                if (save.getSuccess()) {
                    log.info("远程调用会员服务保存用户信息成功");
                    return "redirect:login.html";
                } else {
                    log.warn("远程调用会员服务保存用户信息失败");
                    Map<Object, Object> map = new LinkedHashMap<>(2);
                    map.put("code", repCode);
                    map.put("msg", message);
                    redirectAttributes.addFlashAttribute("errors", map);
                    return "redirect:http://auth.lejing.com/reg.html";
                }
            }
        } else {
            Map<Object, Object> map = new LinkedHashMap<>(1);
            map.put("checkCode", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", map);
            return "redirect:http://auth.lejing.com/reg.html";
        }
        // 注册成功回到登录页
        return "redirect:login.html";
    }
}
