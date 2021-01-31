package cn.alphahub.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuwenjing
 */
@SpringBootApplication
@MapperScans(value = @MapperScan(value = {"cn.**.dao", "cn.**.mapper"}))
public class LejingOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingOrderApplication.class, args);
    }

}
