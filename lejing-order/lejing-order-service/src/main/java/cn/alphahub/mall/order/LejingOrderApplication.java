package cn.alphahub.mall.order;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 乐璟商城 - 订单服务
 * <ul>
 *     <li>使用注解{@code @EnableAspectJAutoProxy(exposeProxy = true)}开启aspect动态代理模式,对外暴露代理对象</li>
 * </ul>
 *
 * @author liuwenjing
 */
@EnableFeignClients(basePackages = {"cn.**.order.feign"})
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScans(value = {@MapperScan(value = {"cn.**.order.dao", "cn.**.order.mapper"})})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall"})
public class LejingOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingOrderApplication.class, args);
    }

}
