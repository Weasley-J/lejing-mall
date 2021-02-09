package cn.alphahub.mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API 网关服务
 *
 * @author liuwenjing
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LejingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingGatewayApplication.class, args);
    }

}
