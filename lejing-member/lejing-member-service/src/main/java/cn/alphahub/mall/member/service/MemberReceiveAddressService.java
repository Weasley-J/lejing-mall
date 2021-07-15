package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 会员收货地址Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    /**
     * 查询会员收货地址分页列表
     *
     * @param pageDomain           分页数据
     * @param memberReceiveAddress 分页对象
     * @return 会员收货地址分页数据
     */
    PageResult<MemberReceiveAddress> queryPage(PageDomain pageDomain, MemberReceiveAddress memberReceiveAddress);

    /**
     * 查询用户的收货地址列表
     *
     * @param memberId 用户id
     * @return 收货地址列表
     */
    List<MemberReceiveAddress> memberAddressList(Long memberId);
}
