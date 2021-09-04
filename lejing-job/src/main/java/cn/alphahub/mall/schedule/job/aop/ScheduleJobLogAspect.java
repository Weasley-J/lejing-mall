package cn.alphahub.mall.schedule.job.aop;

import cn.alphahub.common.annotations.Syslog;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.domain.QuartzJobLog;
import cn.alphahub.mall.schedule.job.service.QuartzJobLogService;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * 任务调度执行日志切面
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-04
 */
@Slf4j
@Aspect
@Component
public class ScheduleJobLogAspect {

    @Resource
    private Scheduler scheduler;
    @Resource
    private QuartzJobService quartzJobService;
    @Resource
    private QuartzJobLogService quartzJobLogService;

    /**
     * 定义切入点方法
     */
    @Pointcut(value = "@annotation(cn.alphahub.common.annotations.Syslog)")
    public void pointcut() {
    }

    /**
     * 目标方法执行之前执行
     */
    @Before("pointcut()")
    public void before(JoinPoint point) {
        System.err.println("切入点所织入目标方法执行之前执行: before()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("ip:{}", request.getRemoteAddr());
        log.info("url:{}", request.getRequestURL().toString());
        log.info("http-method:{}", request.getMethod());
        log.info("class-method:{}", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        log.info("parameters:{}", point.getArgs());
    }

    /**
     * 环绕通知
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.err.println("环绕通知开始：around()");
        long beginTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis() - beginTime;
        System.err.println("\n环绕通知结束：around()，耗时：" + endTime + "（ms），" +
                "开始时间：" + DateUtil.formatDateTime(new Date(beginTime)) + "，" +
                "结束时间：" + DateUtil.formatDateTime(new Date(endTime)));
        return proceed;
    }

    /**
     * 目标方法执行之后必定执行(无论是否报错)
     * <p>
     * 目标方法同时需要{@code @Syslog}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致
     */
    @After("pointcut() && @annotation(syslog)")
    public void after(Syslog syslog) {
        System.err.println("目标方法执行之后必定执行：after()");
        log.debug("注解值:{}", syslog.name());
    }

    /**
     * 目标方法有返回值且正常返回后执行
     * <p>
     * 这里切入点方法的形参名{@code logPointcut()}要与上面注解中的一致
     */
    @AfterReturning(pointcut = "pointcut()", returning = "responseData")
    public void afterReturning(JoinPoint point, Object responseData) {
        System.err.println("目标方法有返回值且正常返回后执行：afterReturning()");
        log.info("目标方法有返回值且正常返回后执行，响应数据：{}", responseData);

        Object[] args = point.getArgs();
        String jobName = args[0].toString();
        String jobGroup = args[1].toString();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        QuartzJob quartzJob = quartzJobService.getOne(Wrappers.lambdaQuery(QuartzJob.class)
                .eq(QuartzJob::getJobName, jobName)
                .eq(StringUtils.isNotBlank(jobGroup), QuartzJob::getJobGroup, jobGroup)
                .last(" LIMIT 1"));

        if (Objects.nonNull(quartzJob)) {
            QuartzJobLog jobLog = new QuartzJobLog();
            jobLog.setJobId(quartzJob.getId());
            jobLog.setJobName(quartzJob.getJobName());
            jobLog.setJobGroup(quartzJob.getJobGroup());
            jobLog.setJobDescription(quartzJob.getJobDescription());
            jobLog.setJobClass(quartzJob.getJobClass());
            jobLog.setStatus(quartzJob.getStatus());
            jobLog.setCreateTime(LocalDateTime.now());
            quartzJobLogService.save(jobLog);
        }
    }

    /**
     * 目标方法抛出异常后执行
     * <p>
     * 目标方法同时需要{@code @Syslog}注解的修饰，并且这里（通知）的形参名要与上面注解中的一致,可以声明来获取目标方法抛出的异常
     */
    @AfterThrowing(pointcut = "pointcut() && @annotation(syslog)", throwing = "throwable")
    public void afterThrowing(Syslog syslog, Throwable throwable) {
        System.err.println("目标方法抛出异常后执行'afterThrowing()',注解值'" + syslog.name() + "',异常信息'" + throwable.getMessage() + "'");
    }

}
