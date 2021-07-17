package cn.alphahub.mall.auth.service.impl;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enums.CheckCodeOrigin;
import cn.alphahub.common.enums.CheckCodeStatus;
import cn.alphahub.mall.auth.domain.UserLogin;
import cn.alphahub.mall.auth.domain.UserRegister;
import cn.alphahub.mall.auth.feign.MemberClient;
import cn.alphahub.mall.auth.service.AuthService;
import cn.alphahub.mall.auth.util.CodecUtils;
import cn.alphahub.mall.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <b>授权服务-业务实现</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    /**
     * 授权服务域名
     */
    private static final String AUTH_LEJING_DOMAIN = "http://auth.lejing.com";
    /**
     * 商城首页
     */
    private static final String LEJING_DOMAIN = "http://lejing.com";
    @Resource
    private MemberClient memberClient;
    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * <b>发送验证码</b>
     * <p>使用RabbitMQ发送短信，使用Redis做短信防刷</p>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 验证码发送结果枚举提示
     */
    @Override
    public CheckCodeStatus sendCheckCode(String phone, Integer origin) {
        String splitString = ",";
        // 手机号为空直接返回
        if (StringUtils.isBlank(phone)) {
            return CheckCodeStatus.EMPTY_PHONE;
        }
        // 如果redis里面存在则不需要重复发送, 防止恶意刷短信
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String cacheKey = AuthConstant.REDIS_KEY_PREFIX + phone;
        String redisExistsCode = ops.get(cacheKey);
        // 短信防刷逻辑
        if (StringUtils.isNotBlank(redisExistsCode)) {
            String[] split = redisExistsCode.split(splitString);
            long parsedRetrySeconds = Long.parseLong(split[1]);
            long retrySeconds = 120L;
            long retryMillis = retrySeconds * 1000L;
            // 120秒内不能重发
            CheckCodeStatus send = CheckCodeStatus.SEND;
            if (System.currentTimeMillis() - parsedRetrySeconds < retryMillis) {
                log.warn("redis里面存在该手机号对应的验证码，" + retrySeconds + "秒内不能要重复发送!");
                send.setName("请求短信验证码频次太高，" + retrySeconds + "秒内不能重发！");
                return send;
            }
        }
        //获取验证码长度:移动端4位,浏览器6位, origin:1-使用移动端请求验证码,2-使用浏览器请求验证码,0-未知来源, origin为空验证码长度6位
        int checkCodeLength = Objects.nonNull(origin) ? Objects.equals(CheckCodeOrigin.MOBILE.getValue(), origin) ? 4 : 6 : 6;
        //生成验证码
        String code = RandomStringUtils.randomNumeric(checkCodeLength);
        Map<String, Object> msgMap = new HashMap<>(2);
        msgMap.put("code", code);
        msgMap.put("phone", phone);
        //发消息到RabbitMQ
        try {
            // routingKey要和消息对列中的保持一致
            // 提示: 我们已经在配置文件指定用于发送操的默认routing key了,这里就不指定routing key了, 如果指定routingKey: mqpTemplate.convertAndSend("sms.verify.code", msgMap)
            amqpTemplate.convertAndSend(msgMap);
            // 給验证码拼上当前系统的时间戳, 保存验证码到redis缓存中, 15min 内有效;
            code = code + splitString + System.currentTimeMillis();
            ops.set(cacheKey, code, 15, TimeUnit.MINUTES);
            return CheckCodeStatus.SUCCESS;
        } catch (Exception e) {
            log.error("发送短信失败! phone：{}， code：{}, 异常信息:{}\n", phone, code, e.getMessage(), e);
            return CheckCodeStatus.ERROR;
        }
    }

    /**
     * 用户注册
     *
     * @param userRegister       注册提交的用户信息
     * @param redirectAttributes 模拟重定向携带数据,重定向也可以保留数据，不会丢失
     * @return 登录页|注册页面
     */
    @Override
    public String register(UserRegister userRegister, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Map<Object, Object> map = result.getFieldErrors().stream().collect(
                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
            );
            redirectAttributes.addFlashAttribute("errors", map);
            // 校验错误转发到注册页面
            return "redirect:" + AUTH_LEJING_DOMAIN + "/reg.html";
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
                        .nickname(userRegister.getUserName())
                        .build();
                BaseResult<Boolean> save = memberClient.save(member);
                String message = save.getMessage();
                Integer repCode = save.getCode();
                log.info("响应状态码：" + repCode + "；响应消息：" + message);
                // 保存用户信息成功
                if (save.getSuccess()) {
                    log.info("远程调用会员服务保存用户信息成功");
                    return "redirect:" + AUTH_LEJING_DOMAIN + "/login.html";
                } else {
                    log.warn("远程调用会员服务保存用户信息失败");
                    Map<Object, Object> map = new LinkedHashMap<>(2);
                    map.put("code", repCode);
                    map.put("msg", message);
                    redirectAttributes.addFlashAttribute("errors", map);
                    return "redirect:" + AUTH_LEJING_DOMAIN + "/reg.html";
                }
            }
        } else {
            Map<Object, Object> map = new LinkedHashMap<>(1);
            map.put("checkCode", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", map);
            return "redirect:" + AUTH_LEJING_DOMAIN + "/reg.html";
        }
        // 注册成功回到登录页
        return "redirect:" + AUTH_LEJING_DOMAIN + "/login.html";
    }

    /**
     * 用户登录
     *
     * @param user               用户信息
     * @param redirectAttributes 模拟重定向携带数据,重定向也可以保留数据，不会丢失
     * @param session            session
     * @return 首页
     */
    @Override
    public String login(UserLogin user, RedirectAttributes redirectAttributes, HttpSession session) {
        Map<String, Object> errMap = new LinkedHashMap<>(2);
        String passwordRaw = user.getPassword();
        BaseResult<Member> res = getMemberLoginRequestResult(user, passwordRaw);
        System.out.println(res);
        Integer code = res.getCode();
        String msg = res.getMessage();
        if (res.getSuccess()) {
            log.info("code: {}, msg:{}", code, msg);
            Member member = res.getData();
            String encodedPassword = member.getPassword();
            // 验证前端提交的密码是否正确
            Boolean matched = CodecUtils.matchesPassword(passwordRaw, encodedPassword);
            if (matched.equals(true)) {
                msg = "[" + user.getLoginacct() + "]的密码正确";
                log.info(msg);
                errMap.put("code", code);
                errMap.put("msg", msg);
                session.setAttribute(AuthConstant.LOGIN_USER, member);
                redirectAttributes.addFlashAttribute("errors", errMap);
                return "redirect:" + LEJING_DOMAIN;
            } else {
                msg = "[" + user.getLoginacct() + "]的密码错误";
                log.warn(msg);
                errMap.put("code", code);
                errMap.put("msg", msg);
            }
        } else {
            msg = "用户[" + user.getLoginacct() + "]不存在";
            log.warn(msg);
        }
        return "redirect:" + AUTH_LEJING_DOMAIN + "/login.html";
    }

    /**
     * 获取远程会员登录结果
     *
     * @param user        用户信息
     * @param passwordRaw 原明文密码
     * @return 远程会员登录结果
     */
    private BaseResult<Member> getMemberLoginRequestResult(UserLogin user, String passwordRaw) {
        BaseResult<Member> res = memberClient.login(Member.builder()
                // 用户名
                .username(user.getLoginacct())
                .password(passwordRaw)
                .build());
        Member member = res.getData();
        if (Objects.isNull(member)) {
            res = memberClient.login(Member.builder()
                    // 手机
                    .mobile(user.getLoginacct())
                    .password(passwordRaw)
                    .build());
        }
        if (Objects.isNull(member)) {
            res = memberClient.login(Member.builder()
                    // 邮箱
                    .email(user.getLoginacct())
                    .password(passwordRaw)
                    .build());
        }
        return res;
    }
}
