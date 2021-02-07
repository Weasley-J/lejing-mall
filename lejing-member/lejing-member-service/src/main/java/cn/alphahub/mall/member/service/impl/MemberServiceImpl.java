package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberMapper;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.service.MemberService;

import java.util.List;

/**
 * 会员Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    /**
     * 查询会员分页列表
     *
     * @param pageDomain   分页数据
     * @param member 分页对象
     * @return 会员分页数据
     */
    @Override
    public PageResult<Member> queryPage(PageDomain pageDomain, Member member) {
        pageDomain.startPage();
        QueryWrapper<Member> wrapper = new QueryWrapper<>(member);
        List<Member> list = this.list(wrapper);
        PageInfo<Member> pageInfo = new PageInfo<>(list);
        PageResult<Member> pageResult = PageResult.<Member>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}