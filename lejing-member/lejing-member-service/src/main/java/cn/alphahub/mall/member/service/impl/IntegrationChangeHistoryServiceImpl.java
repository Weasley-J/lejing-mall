package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import cn.alphahub.mall.member.mapper.IntegrationChangeHistoryMapper;
import cn.alphahub.mall.member.service.IntegrationChangeHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分变化历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class IntegrationChangeHistoryServiceImpl extends ServiceImpl<IntegrationChangeHistoryMapper, IntegrationChangeHistory> implements IntegrationChangeHistoryService {

    /**
     * 查询积分变化历史记录分页列表
     *
     * @param pageDomain               分页数据
     * @param integrationChangeHistory 分页对象
     * @return 积分变化历史记录分页数据
     */
    @Override
    public PageResult<IntegrationChangeHistory> queryPage(PageDomain pageDomain, IntegrationChangeHistory integrationChangeHistory) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<IntegrationChangeHistory> wrapper = new QueryWrapper<>(integrationChangeHistory);
        // 2. 创建一个分页对象
        PageResult<IntegrationChangeHistory> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<IntegrationChangeHistory> integrationChangeHistoryList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(integrationChangeHistoryList);
    }

}
