package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderOperateHistoryMapper;
import cn.alphahub.mall.order.domain.OrderOperateHistory;
import cn.alphahub.mall.order.service.OrderOperateHistoryService;

import java.util.List;

/**
 * 订单操作历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@Service("orderOperateHistoryService")
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

    /**
     * 查询订单操作历史记录分页列表
     *
     * @param pageDomain   分页数据
     * @param orderOperateHistory 分页对象
     * @return 订单操作历史记录分页数据
     */
    @Override
    public PageResult<OrderOperateHistory> queryPage(PageDomain pageDomain, OrderOperateHistory orderOperateHistory) {
        pageDomain.startPage();
        QueryWrapper<OrderOperateHistory> wrapper = new QueryWrapper<>(orderOperateHistory);
        List<OrderOperateHistory> list = this.list(wrapper);
        PageInfo<OrderOperateHistory> pageInfo = new PageInfo<>(list);
        PageResult<OrderOperateHistory> pageResult = PageResult.<OrderOperateHistory>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}