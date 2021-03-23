package cn.alphahub.mall.auth.feign;

import cn.alphahub.mall.member.api.MemberApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员Controller - feign远程客户端
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021年3月23日
 */
@FeignClient(name = "lejing-member")
public interface MemberClient extends MemberApi {

}
