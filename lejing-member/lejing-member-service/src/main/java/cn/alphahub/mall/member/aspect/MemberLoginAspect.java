package cn.alphahub.mall.member.aspect;

import cn.alphahub.common.annotations.Syslog;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.ip.AddressUtil;
import cn.alphahub.common.util.ip.IpUtil;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import cn.alphahub.mall.member.service.MemberLoginLogService;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * 记录用户登录日志切面类
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/08/19
 */
@Slf4j
@Aspect
@Component
public class MemberLoginAspect {

    @Resource
    private MemberLoginLogService loginLogService;

    /**
     * 定义切入点方法
     */
    @Pointcut(value = "@annotation(cn.alphahub.common.annotations.Syslog)")
    public void logPointcut() {
    }

    /**
     * 目标方法执行之前执行
     */
    @Before("logPointcut()")
    public void doBefore(JoinPoint point) {
        System.err.println("切入点所织入目标方法执行之前执行：doBefore()");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 记录下请求内容
        log.info("IP:[{}]", request.getRemoteAddr());
        log.info("URL:[{}]", request.getRequestURL().toString());
        log.info("HTTP_METHOD:[{}]", request.getMethod());
        log.info("CLASS_METHOD:[{}]", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        log.info("ARGS:{}", point.getArgs());
    }

    /**
     * 环绕通知
     */
    @Around("logPointcut()")
    @Transactional(rollbackFor = Exception.class)
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.err.println("环绕通知开始：around()");
        long beginTime = System.currentTimeMillis();
        Object proceed = null;
        proceed = point.proceed();
        long endTime = System.currentTimeMillis() - beginTime;
        System.err.println("环绕通知结束：around()，耗时：" + endTime + "（ms）");
        return proceed;
    }

    /**
     * 目标方法执行之后必定执行(无论是否报错)
     * <p>
     * 目标方法同时需要{@code @Syslog}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致
     */
    @After("logPointcut() && @annotation(syslog)")
    public void doAfter(Syslog syslog) {
        System.err.println("目标方法执行之后必定执行：doAfter()");
        log.debug("注解值[{}]", syslog.name());
    }

    /**
     * 目标方法有返回值且正常返回后执行
     * <p>
     * 这里（切入点）的形参名{@code logPointcut()}要与上面注解中的一致
     */
    @AfterReturning(pointcut = "logPointcut()", returning = "responseData")
    public void doAfterReturning(JoinPoint point, Object responseData) {
        System.err.println("目标方法有返回值且正常返回后执行：doAfterReturning()");
        log.info("目标方法有返回值且正常返回后执行，响应数据：{}", responseData);
        Object[] args = point.getArgs();
        JSON json = JSONUtil.parse(args[0]);
        Member member = json.toBean(Member.class);
        log.info("入参：{}", JSONUtil.toJsonPrettyStr(member));

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        BaseResult<Member> result = JSONUtil.toBean(JSONUtil.parse(responseData), new TypeReference<>() {
        }, true);
        MemberLoginLog entity = MemberLoginLog.builder()
                .memberId(result.getData().getId())
                .createTime(new Date())
                .ip(IpUtil.getCleintIp(request))
                .city(AddressUtil.getRealAddressByIP(IpUtil.getCleintIp(request)))
                .loginType(1).build();
        //保存会员登录日志
        loginLogService.save(entity);
        log.debug("会员登录信息：{}", JSONUtil.toJsonPrettyStr(entity));
    }

    /**
     * 目标方法抛出异常后执行
     * <p>
     * 目标方法同时需要{@code @Syslog}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致,可以声明来获取目标方法抛出的异常
     */
    @AfterThrowing(pointcut = "logPointcut() && @annotation(syslog)", throwing = "throwable")
    public void doAfterThrowing(Syslog syslog, Throwable throwable) {
        log.error("目标方法抛出异常后执行:{}", throwable.getMessage());
        log.debug("目标方法抛出异常后执行,注解值[{}]", syslog.name());
    }

}
