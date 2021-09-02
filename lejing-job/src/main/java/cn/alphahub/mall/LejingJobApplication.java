package cn.alphahub.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 乐璟商城 - 任务调度服务
 *
 * @author liuwenjing
 */
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.**.schedule.feign"})
@MapperScan(value = {"cn.**.job.mapper", "cn.**.sys.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall"})
public class LejingJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingJobApplication.class, args);
    }

}
