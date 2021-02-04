package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberStatisticsInfoMapper;
import cn.alphahub.mall.member.domain.MemberStatisticsInfo;
import cn.alphahub.mall.member.service.MemberStatisticsInfoService;

import java.util.List;

/**
 * 会员统计信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Service("memberStatisticsInfoService")
public class MemberStatisticsInfoServiceImpl extends ServiceImpl<MemberStatisticsInfoMapper, MemberStatisticsInfo> implements MemberStatisticsInfoService {

    /**
     * 查询会员统计信息分页列表
     *
     * @param pageDomain   分页数据
     * @param memberStatisticsInfo 分页对象
     * @return 会员统计信息分页数据
     */
    @Override
    public PageResult<MemberStatisticsInfo> queryPage(PageDomain pageDomain, MemberStatisticsInfo memberStatisticsInfo) {
        pageDomain.startPage();
        QueryWrapper<MemberStatisticsInfo> wrapper = new QueryWrapper<>(memberStatisticsInfo);
        List<MemberStatisticsInfo> list = this.list(wrapper);
        PageInfo<MemberStatisticsInfo> pageInfo = new PageInfo<>(list);
        PageResult<MemberStatisticsInfo> pageResult = PageResult.<MemberStatisticsInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}