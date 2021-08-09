package cn.alphahub.mall.reserve;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 乐璟商城 - 场地预约
 *
 * @author liuwenjing
 */
@EnableDiscoveryClient
@MapperScans(value = {@MapperScan(value = {"cn.**.dao", "cn.**.mapper"})})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall.reserve", "cn.alphahub.mall.base"})
public class LejingSiteReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingSiteReserveApplication.class, args);
    }

}
