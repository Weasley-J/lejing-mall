package cn.alphahub.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 *
 * @author liuwenjing
 */
public class ThreadUtil {

    /**
     * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式
     */
    private static final ExecutorService POOL = new ThreadPoolExecutor(
            15,
            100,
            10L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1024),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void execute(Runnable runnable) {
        POOL.submit(runnable);
    }
}
