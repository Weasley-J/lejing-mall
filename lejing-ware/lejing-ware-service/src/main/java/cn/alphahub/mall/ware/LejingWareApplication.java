package cn.alphahub.mall.ware;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 乐璟商城-库存服务
 *
 * @author liuwenjing
 */
@EnableFeignClients(basePackages = {"cn.**.ware.feign"})
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@EnableTransactionManagement
@MapperScans(value = {@MapperScan(value = {"cn.**.ware.dao", "cn.**.ware.mapper"})})
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall.ware", "cn.alphahub.mall.base"})
public class LejingWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingWareApplication.class, args);
    }

}
