package cn.alphahub.mall.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LejingCommonAppTest {

    public static void main(String[] args) {
        SpringApplication.run(LejingCommonAppTest.class, args);
    }

}
