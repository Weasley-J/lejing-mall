package cn.alphahub.mall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuwenjing
 */
@SpringBootApplication
@MapperScans(value = {@MapperScan(value = {"cn.**.dao", "cn.**.mapper"})})
public class LejingWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingWareApplication.class, args);
    }

}
