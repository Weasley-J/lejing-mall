package cn.alphahub.mall.sms.aspect;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.config.SmsConfig;
import cn.alphahub.mall.sms.enums.SmsSupplier;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static cn.alphahub.mall.sms.SmsClient.DefaultSmsClientPlaceholder;
import static cn.alphahub.mall.sms.config.SmsConfig.SmsTemplateProperties;

/**
 * 多模板短信配置切面
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class SmsAspect {
    /**
     * sms client thread local
     */
    private final ThreadLocal<SmsClient> smsClientThreadLocal = new ThreadLocal<>();
    /**
     * 多模板短信配置
     */
    @Resource
    private SmsConfig smsConfig;
    /**
     * 多模板、多供应商短信发送实例对象map集合
     */
    @Resource
    private Map<String, SmsClient> smsClientMap;
    /**
     * 默认短信模板配置元数据
     */
    @Resource
    private SmsTemplateProperties templateProperties;

    /**
     * 定义切入点方法
     */
    @Pointcut(value = "@annotation(cn.alphahub.mall.sms.annotation.SMS)")
    public void pointcut() {
        // a void method for aspect pointcut.
    }

    /**
     * 目标方法执行之前执行
     */
    @Before("pointcut() && @annotation(sms)")
    public void before(JoinPoint point, SMS sms) throws Exception {
        log.info("1. before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String templateName = smsConfig.decorateTemplateName(sms.supplier(), sms.name());
        SmsClient smsClient = smsClientMap.get(templateName);
        // 自定义实现发送发送短信的实现类不为空时，将优先使用自定义短信实现
        if (Objects.nonNull(sms.invokeClass()) && sms.invokeClass() != DefaultSmsClientPlaceholder.class) {
            SmsClient instance = sms.invokeClass().getDeclaredConstructor().newInstance();
            smsClientThreadLocal.set(instance);
        } else {
            smsClientThreadLocal.set(smsClient);
        }
    }

    /**
     * 环绕通知
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("2. around");
        long beginTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis() - beginTime;
        log.warn("2. around耗时：{}（ms），开始时间：{}，结束时间：{}", endTime, DateUtil.formatDateTime(new Date(beginTime)), DateUtil.formatDateTime(new Date(endTime)));
        return proceed;
    }

    /**
     * 目标方法执行之后必定执行(无论是否报错)
     * <p>
     * 目标方法同时需要{@code @SMS}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致
     */
    @After("pointcut() && @annotation(sms)")
    public void after(SMS sms) {
        log.info("3. after");
        smsClientThreadLocal.remove();
    }

    /**
     * 目标方法有返回值且正常返回后执行
     * <p>
     * 这里切入点方法的形参名{@code pointcut()}要与上面注解中的一致
     */
    @AfterReturning(pointcut = "pointcut()", returning = "responseData")
    public void afterReturning(JoinPoint point, Object responseData) {
        log.info("4. afterReturning, responseData: {}", responseData.toString());
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    }

    /**
     * 目标方法抛出异常后执行
     * <p>
     * 目标方法同时需要{@code @SMS}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致,可以声明来获取目标方法抛出的异常
     */
    @AfterThrowing(pointcut = "pointcut() && @annotation(sms)", throwing = "throwable")
    public void afterThrowing(SMS sms, Throwable throwable) {
        log.error("5. afterThrowing, throwable: {}", throwable.getLocalizedMessage());
    }

    /**
     * 获取短信客户端实例
     *
     * @return {@code cn.alphahub.mall.sms.SmsClient}
     */
    public SmsClient getSmsClient() {
        SmsClient smsClient = smsClientThreadLocal.get();
        if (null == smsClient) {
            SmsSupplier smsSupplier = templateProperties.getSmsSupplier();
            String templateName = templateProperties.getTemplateName();
            String decorateTemplateName = smsConfig.decorateTemplateName(smsSupplier, templateName);
            smsClient = smsClientMap.get(decorateTemplateName);
        }
        return smsClient;
    }
}
