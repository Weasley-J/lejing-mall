package cn.alphahub.mall.ware.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.member.api.MemberReceiveAddressApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <b>会员收货地址 - feign远程客户端</b>
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/04/26
 */
@FeignClient(name = AppConstant.MEMBER_SERVICE, contextId = "memberReceiveAddressClient")
public interface MemberReceiveAddressClient extends MemberReceiveAddressApi {

}
