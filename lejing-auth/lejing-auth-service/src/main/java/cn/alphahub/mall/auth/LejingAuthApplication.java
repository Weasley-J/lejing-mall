package cn.alphahub.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 乐璟商城-授权服务
 * <p>
 * Oauth2.0微博开放平台接入: https://open.weibo.com/#_loginLayer_1616777546780<br/>
 * 使用: 网站接入
 * </p>
 * <p>@EnableRedisHttpSession导入RedisHttpSessionConfiguration配置,<br/>
 * 给容器中添加了一个组件, RedisOperationsSessionRepository：Redis操作session，session的增删改查封装类
 * </p>
 *
 * @author liuwenjing
 * @date 2021年3月17日
 */
@EnableDiscoveryClient
@EnableRedisHttpSession
@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.**.auth.feign"})
public class LejingAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LejingAuthApplication.class, args);
    }
}
