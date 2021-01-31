package cn.alphahub.mall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScans(value = {@MapperScan(value = {"cn.**.dao", "cn.**.mapper"})})
public class LejingMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(LejingMemberApplication.class, args);
    }

}
