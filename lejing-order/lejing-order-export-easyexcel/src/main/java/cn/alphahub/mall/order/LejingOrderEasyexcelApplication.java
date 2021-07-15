package cn.alphahub.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 乐璟商城 - 订单服务
 * <p>easyexcel数据导出完整示例</p>
 * <ul>
 *     <li>使用注解{@code @EnableAspectJAutoProxy(exposeProxy = true)}开启aspect动态代理模式,对外暴露代理对象</li>
 * </ul>
 *
 * @author liuwenjing
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.**.order.feign"})
@MapperScans(value = {@MapperScan(value = {"cn.**.order.dao", "cn.**.order.mapper"})})
public class LejingOrderEasyexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingOrderEasyexcelApplication.class, args);
    }

}
