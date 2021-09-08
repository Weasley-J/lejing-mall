package cn.alphahub.mall.email.aspect;

import cn.alphahub.mall.email.annotation.Email;
import cn.alphahub.mall.email.config.EmailConfig;
import cn.hutool.core.date.DateUtil;
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
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 邮件模板切面
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-07 10:51
 */
@Slf4j
@Aspect
@Component
public class EmailAspect {
    /**
     * mail sender thread local
     */
    private final ThreadLocal<JavaMailSender> mailSenderThreadLocal = new ThreadLocal<>();
    /**
     * mail properties thread local
     */
    private final ThreadLocal<MailProperties> mailPropertiesThreadLocal = new ThreadLocal<>();
    /**
     * email config
     */
    @Resource
    private EmailConfig emailConfig;
    /**
     * 填充邮件模板配置列表元数据Map
     */
    @Resource
    private Map<String, MailProperties> emailPropertiesMap;

    /**
     * 定义切入点方法
     */
    @Pointcut(value = "@annotation(cn.alphahub.mall.email.annotation.Email)")
    public void pointcut() {
        // a void method for aspect pointcut.
    }

    /**
     * 目标方法执行之前执行
     */
    @Before("pointcut() && @annotation(email)")
    public void before(JoinPoint point, Email email) {
        log.info("before --------------------------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        MailProperties properties = emailPropertiesMap.get(email.name());
        JavaMailSender mailSender = emailConfig.getMailSender(emailPropertiesMap, email.name());

        mailSenderThreadLocal.set(mailSender);
        mailPropertiesThreadLocal.set(properties);
    }

    /**
     * 环绕通知
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("around --------------------------");
        long beginTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis() - beginTime;
        System.err.println("around耗时：" + endTime + "（ms），" +
                "开始时间：" + DateUtil.formatDateTime(new Date(beginTime)) + "，" +
                "结束时间：" + DateUtil.formatDateTime(new Date(endTime)));
        return proceed;
    }

    /**
     * 目标方法执行之后必定执行(无论是否报错)
     * <p>
     * 目标方法同时需要{@code @Email}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致
     */
    @After("pointcut() && @annotation(email)")
    public void after(Email email) {
        log.info("after --------------------------");
        mailSenderThreadLocal.remove();
        mailPropertiesThreadLocal.remove();
    }

    /**
     * 目标方法有返回值且正常返回后执行
     * <p>
     * 这里切入点方法的形参名{@code pointcut()}要与上面注解中的一致
     */
    @AfterReturning(pointcut = "pointcut()", returning = "responseData")
    public void afterReturning(JoinPoint point, Object responseData) {
        log.info("afterReturning, responseData: {}", responseData.toString());
        System.out.println("afterReturning(),响应数据:" + responseData.toString());
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    }

    /**
     * 目标方法抛出异常后执行
     * <p>
     * 目标方法同时需要{@code @Email}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致,可以声明来获取目标方法抛出的异常
     */
    @AfterThrowing(pointcut = "pointcut() && @annotation(email)", throwing = "throwable")
    public void afterThrowing(Email email, Throwable throwable) {
        log.error("afterThrowing, throwable: {}", throwable.getLocalizedMessage());
    }

    /**
     * 获取邮件是发送实例（ThreadLocal中获取，目标方法后移除线程变量）
     *
     * @return JavaMailSender
     */
    public JavaMailSender javaMailSender() {
        return mailSenderThreadLocal.get();
    }

    /**
     * @return 电子邮件支持的配置属性
     */
    public MailProperties mailProperties() {
        return mailPropertiesThreadLocal.get();
    }
}
