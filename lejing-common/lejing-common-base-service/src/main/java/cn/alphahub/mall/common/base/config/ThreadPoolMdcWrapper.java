package cn.alphahub.mall.common.base.config;

import cn.alphahub.mall.common.base.constant.FrameworkConstant;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

;

public class ThreadPoolMdcWrapper extends ThreadPoolTaskExecutor {
    private static final long serialVersionUID = 3940722618853093830L;

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    public static class ThreadMdcUtil {

        private ThreadMdcUtil() {
            throw new IllegalStateException("Utility class");
        }

        public static void setTraceIdIfAbsent() {
            if (MDC.get(FrameworkConstant.TRACE_ID) == null) {
                MDC.put(FrameworkConstant.TRACE_ID, RandomStringUtils.randomAlphanumeric(10));
            }
        }

        public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
            return () -> {
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                setTraceIdIfAbsent();
                try {
                    return callable.call();
                } finally {
                    MDC.clear();
                }
            };
        }

        public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
            return () -> {
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                //设置traceId
                setTraceIdIfAbsent();
                try {
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        }
    }
}
