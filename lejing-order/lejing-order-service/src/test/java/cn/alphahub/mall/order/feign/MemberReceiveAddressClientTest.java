package cn.alphahub.mall.order.feign;

import cn.alphahub.common.core.domain.BaseResult;
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
        BaseResult<List<MemberReceiveAddress>> result = memberReceiveAddressClient.memberAddressList(1L);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }
}
