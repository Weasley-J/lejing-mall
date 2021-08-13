package cn.alphahub.mall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 乐璟商城 - 会员服务
 *
 * @author liuwenjing
 */
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.**.member.feign"})
@MapperScans(value = {@MapperScan(value = {"cn.**.member.dao", "cn.**.member.mapper"})})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall"})
public class LejingMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingMemberApplication.class, args);
    }

}
