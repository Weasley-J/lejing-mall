package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.member.mapper.MemberReceiveAddressMapper;
import cn.alphahub.mall.member.service.MemberReceiveAddressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员收货地址Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress> implements MemberReceiveAddressService {

    /**
     * 查询会员收货地址分页列表
     *
     * @param pageDomain           分页数据
     * @param memberReceiveAddress 分页对象
     * @return 会员收货地址分页数据
     */
    @Override
    public PageResult<MemberReceiveAddress> queryPage(PageDomain pageDomain, MemberReceiveAddress memberReceiveAddress) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberReceiveAddress> wrapper = new QueryWrapper<>(memberReceiveAddress);
        // 2. 创建一个分页对象
        PageResult<MemberReceiveAddress> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberReceiveAddress> memberReceiveAddressList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberReceiveAddressList);
    }

    @Override
    public List<MemberReceiveAddress> memberAddressList(Long memberId) {
        return this.list(Wrappers.<MemberReceiveAddress>lambdaQuery()
                .eq(MemberReceiveAddress::getMemberId, memberId)
        );
    }

}
