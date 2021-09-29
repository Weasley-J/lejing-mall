package cn.alphahub.mall.thirdparty;

import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 乐璟商城-三方服务
 *
 * @author liuwenjing
 * @date 2021年3月21日17:38:31
 */
@EnableSmsSupport
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall"})
public class LejingThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingThirdPartyApplication.class, args);
    }

}
