package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberReceiveAddressMapper;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.member.service.MemberReceiveAddressService;

import java.util.List;

/**
 * 会员收货地址Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress> implements MemberReceiveAddressService {

    /**
     * 查询会员收货地址分页列表
     *
     * @param pageDomain   分页数据
     * @param memberReceiveAddress 分页对象
     * @return 会员收货地址分页数据
     */
    @Override
    public PageResult<MemberReceiveAddress> queryPage(PageDomain pageDomain, MemberReceiveAddress memberReceiveAddress) {
        pageDomain.startPage();
        QueryWrapper<MemberReceiveAddress> wrapper = new QueryWrapper<>(memberReceiveAddress);
        List<MemberReceiveAddress> list = this.list(wrapper);
        PageInfo<MemberReceiveAddress> pageInfo = new PageInfo<>(list);
        PageResult<MemberReceiveAddress> pageResult = PageResult.<MemberReceiveAddress>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}