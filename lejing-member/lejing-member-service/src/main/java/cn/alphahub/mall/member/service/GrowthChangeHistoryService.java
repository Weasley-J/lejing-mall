package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 成长值变化历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistory> {

    /**
     * 查询成长值变化历史记录分页列表
     *
     * @param pageDomain          分页数据
     * @param growthChangeHistory 分页对象
     * @return 成长值变化历史记录分页数据
     */
    PageResult<GrowthChangeHistory> queryPage(PageDomain pageDomain, GrowthChangeHistory growthChangeHistory);

}
