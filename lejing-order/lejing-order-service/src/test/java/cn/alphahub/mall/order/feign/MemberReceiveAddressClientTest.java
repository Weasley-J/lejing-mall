package cn.alphahub.mall.order.feign;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MemberReceiveAddressClientTest {
    @Resource
    private MemberReceiveAddressClient memberReceiveAddressClient;

    @Test
    void addrList() {
        Result<List<MemberReceiveAddress>> result = memberReceiveAddressClient.memberAddressList(1L);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }
}
