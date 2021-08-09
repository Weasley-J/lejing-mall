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
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@MapperScans(value = {@MapperScan(value = {"cn.**.coupon.dao", "cn.**.coupon.mapper"})})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall.coupon", "cn.alphahub.mall.base"})
public class LejingCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingCouponApplication.class, args);
    }

}
