package cn.alphahub.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 订单服务 - 启动类
 *
 * @author liuwenjing
 */
@EnableRabbit
@EnableFeignClients
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
@MapperScans(value = {@MapperScan(value = {"cn.**.order.dao", "cn.**.order.mapper"})})
public class LejingOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingOrderApplication.class, args);
    }

}
