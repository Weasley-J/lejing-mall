package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberLevelMapper;
import cn.alphahub.mall.member.domain.MemberLevel;
import cn.alphahub.mall.member.service.MemberLevelService;

import java.util.List;

/**
 * 会员等级Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    /**
     * 查询会员等级分页列表
     *
     * @param pageDomain   分页数据
     * @param memberLevel 分页对象
     * @return 会员等级分页数据
     */
    @Override
    public PageResult<MemberLevel> queryPage(PageDomain pageDomain, MemberLevel memberLevel) {
        pageDomain.startPage();
        QueryWrapper<MemberLevel> wrapper = new QueryWrapper<>(memberLevel);
        List<MemberLevel> list = this.list(wrapper);
        PageInfo<MemberLevel> pageInfo = new PageInfo<>(list);
        PageResult<MemberLevel> pageResult = PageResult.<MemberLevel>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}