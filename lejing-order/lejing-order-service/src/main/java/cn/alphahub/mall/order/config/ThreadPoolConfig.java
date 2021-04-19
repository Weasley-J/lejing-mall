package cn.alphahub.mall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <b>自定义线程池配置类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/17
 */
@Configuration
public class ThreadPoolConfig {

    @Resource
    private ThreadPoolProperties threadPoolProperties;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                // 核心线程池
                threadPoolProperties.getCorePoolSize(),
                // 最大线程数
                threadPoolProperties.getMaximumPoolSize(),
                // 存活时间
                threadPoolProperties.getKeepAliveTime(),
                // 时间单位
                threadPoolProperties.getTimeUnit(),
                // 最大任务数量
                new LinkedBlockingQueue<>(threadPoolProperties.getCapacity()),
                // 使用默认线程工厂创建新线程
                Executors.defaultThreadFactory(),
                // 达到线程界限和队列容量而在执行被阻止时使用的拒绝策略
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
