package cn.alphahub.mall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <b>线程池配置参数</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "lejing.thread")
public class ThreadPoolProperties implements Serializable {
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
     * @see java.util.concurrent.TimeUnit
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
