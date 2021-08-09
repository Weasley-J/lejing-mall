package cn.alphahub.mall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 乐璟商城-短信服务
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@SpringBootApplication(scanBasePackages = {"cn.alphahub.mall.sms", "cn.alphahub.mall.base"})
public class LejingSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LejingSmsApplication.class, args);
    }
}
