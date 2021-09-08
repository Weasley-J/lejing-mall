package cn.alphahub.mall.email.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MailSupportDemoTests {

    @Resource
    MailSupportDemo mailSupportDemo;

    @BeforeEach
    void setUp() {
        System.err.println("----------------BeforeEach-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-----------------AfterEach------------------------");
    }

    @Test
    void defaultSend() {
        mailSupportDemo.defaultSend();
    }

    @Test
    void qqEmailSend() {
        mailSupportDemo.qqEmailSend();
    }

    @Test
    void email163Send() {
        mailSupportDemo.email163Send();
    }
}