package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.IntegrationChangeHistoryMapper;
import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import cn.alphahub.mall.member.service.IntegrationChangeHistoryService;

import java.util.List;

/**
 * 积分变化历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Service("integrationChangeHistoryService")
public class IntegrationChangeHistoryServiceImpl extends ServiceImpl<IntegrationChangeHistoryMapper, IntegrationChangeHistory> implements IntegrationChangeHistoryService {

    /**
     * 查询积分变化历史记录分页列表
     *
     * @param pageDomain   分页数据
     * @param integrationChangeHistory 分页对象
     * @return 积分变化历史记录分页数据
     */
    @Override
    public PageResult<IntegrationChangeHistory> queryPage(PageDomain pageDomain, IntegrationChangeHistory integrationChangeHistory) {
        pageDomain.startPage();
        QueryWrapper<IntegrationChangeHistory> wrapper = new QueryWrapper<>(integrationChangeHistory);
        List<IntegrationChangeHistory> list = this.list(wrapper);
        PageInfo<IntegrationChangeHistory> pageInfo = new PageInfo<>(list);
        PageResult<IntegrationChangeHistory> pageResult = PageResult.<IntegrationChangeHistory>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}