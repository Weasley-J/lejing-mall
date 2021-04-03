package cn.alphahub.mall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * <b>乐景商城-购物车服务</b>
 *
 * @author liuwenjing
 * @date 2021年4月3日
 */
@EnableFeignClients
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
public class LejingCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingCartApplication.class, args);
    }

}
