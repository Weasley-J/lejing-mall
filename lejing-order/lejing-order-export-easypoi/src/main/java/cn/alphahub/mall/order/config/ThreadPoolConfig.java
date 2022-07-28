package cn.alphahub.mall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static cn.alphahub.mall.order.config.ThreadPoolConfig.ThreadPoolProperties;

/**
 * <b>自定义线程池配置类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/17
 */
@Configuration
@EnableConfigurationProperties({ThreadPoolProperties.class})
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
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

    /**
     * <b>线程池配置参数</b>
     *
     * @author liuwenjing
     * @version 1.0
     * @date 2021/03/17
     */
    @Data
    @ConfigurationProperties(prefix = "lejing.thread")
    public static class ThreadPoolProperties implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 核心线程池数量
         */
        private Integer corePoolSize;
        /**
         * 最大线程数
         */
        private Integer maximumPoolSize;
        /**
         * 时间单位
         *
         * @see TimeUnit
         */
        private TimeUnit timeUnit;
        /**
         * 存活时间
         */
        private Long keepAliveTime;
        /**
         * 最大任务数量
         */
        private Integer capacity;
    }
}
