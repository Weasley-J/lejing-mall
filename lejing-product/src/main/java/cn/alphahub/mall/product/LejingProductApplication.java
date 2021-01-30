package cn.alphahub.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScans(value = @MapperScan(value = {"cn.**.dao", "cn.**.mapper"}))
public class LejingProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingProductApplication.class, args);
    }
}
