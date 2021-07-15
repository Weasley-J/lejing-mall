package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import cn.alphahub.mall.member.mapper.GrowthChangeHistoryMapper;
import cn.alphahub.mall.member.service.GrowthChangeHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成长值变化历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class GrowthChangeHistoryServiceImpl extends ServiceImpl<GrowthChangeHistoryMapper, GrowthChangeHistory> implements GrowthChangeHistoryService {

    /**
     * 查询成长值变化历史记录分页列表
     *
     * @param pageDomain          分页数据
     * @param growthChangeHistory 分页对象
     * @return 成长值变化历史记录分页数据
     */
    @Override
    public PageResult<GrowthChangeHistory> queryPage(PageDomain pageDomain, GrowthChangeHistory growthChangeHistory) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<GrowthChangeHistory> wrapper = new QueryWrapper<>(growthChangeHistory);
        // 2. 创建一个分页对象
        PageResult<GrowthChangeHistory> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<GrowthChangeHistory> growthChangeHistoryList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(growthChangeHistoryList);
    }

}
