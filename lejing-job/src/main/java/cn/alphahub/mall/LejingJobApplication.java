package cn.alphahub.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 乐璟商城 - 任务调度服务
 *
 * @author liuwenjing
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.alphahub.mall.schedule.job.feign"})
@MapperScan(value = {"cn.**.job.mapper", "cn.**.sys.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall.schedule"})
public class LejingJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingJobApplication.class, args);
    }

}
