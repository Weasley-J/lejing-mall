package cn.alphahub.mall.thirdparty.sms.service.impl;

import cn.alphahub.common.core.domain.SmsParam;
import cn.alphahub.mall.thirdparty.sms.util.AliyunSmsUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SmsServiceImplTest {

    @Resource
    AliyunSmsUtil smsUtil;

    @BeforeEach
    void setUp() {
        System.out.println("---------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------");
    }

    @Test
    void sendSms() {
        smsUtil.sendSms(
                SmsParam.builder().code("123456").phone(new String[]{"19121716816"}).build()
        );
    }

}
