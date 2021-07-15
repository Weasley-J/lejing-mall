package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.member.api.MemberApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <b>会员信息 - feign远程客户端</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 */
@FeignClient(name = AppConstant.MEMBER_SERVICE, contextId = "memberClient")
public interface MemberClient extends MemberApi {

}
