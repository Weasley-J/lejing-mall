package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberStatisticsInfo;
import cn.alphahub.mall.member.mapper.MemberStatisticsInfoMapper;
import cn.alphahub.mall.member.service.MemberStatisticsInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员统计信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberStatisticsInfoServiceImpl extends ServiceImpl<MemberStatisticsInfoMapper, MemberStatisticsInfo> implements MemberStatisticsInfoService {

    /**
     * 查询会员统计信息分页列表
     *
     * @param pageDomain           分页数据
     * @param memberStatisticsInfo 分页对象
     * @return 会员统计信息分页数据
     */
    @Override
    public PageResult<MemberStatisticsInfo> queryPage(PageDomain pageDomain, MemberStatisticsInfo memberStatisticsInfo) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberStatisticsInfo> wrapper = new QueryWrapper<>(memberStatisticsInfo);
        // 2. 创建一个分页对象
        PageResult<MemberStatisticsInfo> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberStatisticsInfo> memberStatisticsInfoList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberStatisticsInfoList);
    }

}
