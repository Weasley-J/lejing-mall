package cn.alphahub.mall.order.feign;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.member.domain.Member;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MemberClientTest {
    @Resource
    private MemberClient memberClient;

    @BeforeEach
    void setUp() {
        System.out.println("+++++++++++++++++++++++");
    }

    @AfterEach
    void tearDown() {
        System.out.println("+++++++++++++++++++++++");
    }

    @Test
    void memberInfo() {
        Result<Member> info = memberClient.info(1L);
        System.out.println(JSONUtil.toJsonPrettyStr(info));
    }
}
