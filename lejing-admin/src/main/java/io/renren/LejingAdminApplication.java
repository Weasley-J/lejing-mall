/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 乐璟商城-后台服务
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.renren", "cn.alphahub.mall"})
@MapperScan(value = {"io.renren.**.dao"})
public class LejingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingAdminApplication.class, args);
    }

}
