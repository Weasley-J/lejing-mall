package cn.alphahub.mall.product;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 乐璟商城 - 商品服务
 *
 * @author liuwenjing
 */
@EnableCaching
@EnableAutoDataSourceProxy
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.**.product.feign"})
@MapperScans(value = {@MapperScan(value = {"cn.**.product.dao", "cn.**.product.mapper"})})
public class LejingProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(LejingProductApplication.class, args);
    }
}
