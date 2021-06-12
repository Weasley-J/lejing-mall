package cn.alphahub.mall.coupon;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 乐璟商城-优惠券
 *
 * @author liuwenjing
 */
@EnableAutoDataSourceProxy
@EnableDiscoveryClient
@SpringBootApplication
@MapperScans(value = {@MapperScan(value = {"cn.**.dao", "cn.**.mapper"})})
public class LejingCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingCouponApplication.class, args);
    }

}
