package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderOperateHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单操作历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistory> {

    /**
     * 查询订单操作历史记录分页列表
     *
     * @param pageDomain          分页数据
     * @param orderOperateHistory 分页对象
     * @return 订单操作历史记录分页数据
     */
    PageResult<OrderOperateHistory> queryPage(PageDomain pageDomain, OrderOperateHistory orderOperateHistory);

}
