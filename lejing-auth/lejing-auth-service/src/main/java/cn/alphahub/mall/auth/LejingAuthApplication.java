package cn.alphahub.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 乐璟商城-授权服务
 *
 * @author liuwenjing
 * @date 2021年3月17日
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LejingAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LejingAuthApplication.class, args);
    }
}
