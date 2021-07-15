package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberStatisticsInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员统计信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfo> {

    /**
     * 查询会员统计信息分页列表
     *
     * @param pageDomain           分页数据
     * @param memberStatisticsInfo 分页对象
     * @return 会员统计信息分页数据
     */
    PageResult<MemberStatisticsInfo> queryPage(PageDomain pageDomain, MemberStatisticsInfo memberStatisticsInfo);

}
