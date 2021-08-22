package cn.alphahub.mall.job;

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
@EnableFeignClients(basePackages = {"cn.**.job.feign"})
@MapperScan(value = {"cn.**.member.dao", "cn.**.member.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall"})
public class LejingJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingJobApplication.class, args);
    }

}
