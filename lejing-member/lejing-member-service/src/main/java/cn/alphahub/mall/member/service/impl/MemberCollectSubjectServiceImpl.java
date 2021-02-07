package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberCollectSubjectMapper;
import cn.alphahub.mall.member.domain.MemberCollectSubject;
import cn.alphahub.mall.member.service.MemberCollectSubjectService;

import java.util.List;

/**
 * 会员收藏的专题活动Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@Service("memberCollectSubjectService")
public class MemberCollectSubjectServiceImpl extends ServiceImpl<MemberCollectSubjectMapper, MemberCollectSubject> implements MemberCollectSubjectService {

    /**
     * 查询会员收藏的专题活动分页列表
     *
     * @param pageDomain   分页数据
     * @param memberCollectSubject 分页对象
     * @return 会员收藏的专题活动分页数据
     */
    @Override
    public PageResult<MemberCollectSubject> queryPage(PageDomain pageDomain, MemberCollectSubject memberCollectSubject) {
        pageDomain.startPage();
        QueryWrapper<MemberCollectSubject> wrapper = new QueryWrapper<>(memberCollectSubject);
        List<MemberCollectSubject> list = this.list(wrapper);
        PageInfo<MemberCollectSubject> pageInfo = new PageInfo<>(list);
        PageResult<MemberCollectSubject> pageResult = PageResult.<MemberCollectSubject>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}