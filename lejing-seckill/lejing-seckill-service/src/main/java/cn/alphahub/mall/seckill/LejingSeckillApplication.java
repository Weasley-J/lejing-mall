package cn.alphahub.mall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 乐璟商城 - 秒杀服务
 *
 * @author liuwenjing
 */
@EnableAsync
@EnableScheduling
@EnableRedisHttpSession
@EnableFeignClients(basePackages = {"cn.alphahub.mall.seckill.**.feign"})
@EnableDiscoveryClient
@SpringBootApplication
public class LejingSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingSeckillApplication.class, args);
    }

}
