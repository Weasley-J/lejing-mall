package cn.alphahub.mall.auth.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.member.api.MemberApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 会员Controller - feign远程客户端
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021年3月23日
 */
@FeignClient(name = AppConstant.MEMBER_SERVICE, contextId = "memberClient")
public interface MemberClient extends MemberApi {

}
