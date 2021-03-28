package cn.alphahub.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 商品服务-启动类
 *
 * @author liuwenjing
 */
@EnableCaching
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.**.product.feign"})
@MapperScans(value = {@MapperScan(value = {"cn.**.dao", "cn.**.mapper"})})
public class LejingProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(LejingProductApplication.class, args);
    }
}
