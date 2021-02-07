package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.GrowthChangeHistoryMapper;
import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import cn.alphahub.mall.member.service.GrowthChangeHistoryService;

import java.util.List;

/**
 * 成长值变化历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@Service("growthChangeHistoryService")
public class GrowthChangeHistoryServiceImpl extends ServiceImpl<GrowthChangeHistoryMapper, GrowthChangeHistory> implements GrowthChangeHistoryService {

    /**
     * 查询成长值变化历史记录分页列表
     *
     * @param pageDomain   分页数据
     * @param growthChangeHistory 分页对象
     * @return 成长值变化历史记录分页数据
     */
    @Override
    public PageResult<GrowthChangeHistory> queryPage(PageDomain pageDomain, GrowthChangeHistory growthChangeHistory) {
        pageDomain.startPage();
        QueryWrapper<GrowthChangeHistory> wrapper = new QueryWrapper<>(growthChangeHistory);
        List<GrowthChangeHistory> list = this.list(wrapper);
        PageInfo<GrowthChangeHistory> pageInfo = new PageInfo<>(list);
        PageResult<GrowthChangeHistory> pageResult = PageResult.<GrowthChangeHistory>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}