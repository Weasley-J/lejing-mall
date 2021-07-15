package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 积分变化历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistory> {

    /**
     * 查询积分变化历史记录分页列表
     *
     * @param pageDomain               分页数据
     * @param integrationChangeHistory 分页对象
     * @return 积分变化历史记录分页数据
     */
    PageResult<IntegrationChangeHistory> queryPage(PageDomain pageDomain, IntegrationChangeHistory integrationChangeHistory);

}
