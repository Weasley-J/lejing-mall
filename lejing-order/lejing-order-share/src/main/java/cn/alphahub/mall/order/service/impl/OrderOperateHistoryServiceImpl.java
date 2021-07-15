package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderOperateHistory;
import cn.alphahub.mall.order.mapper.OrderOperateHistoryMapper;
import cn.alphahub.mall.order.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单操作历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

    /**
     * 查询订单操作历史记录分页列表
     *
     * @param pageDomain          分页数据
     * @param orderOperateHistory 分页对象
     * @return 订单操作历史记录分页数据
     */
    @Override
    public PageResult<OrderOperateHistory> queryPage(PageDomain pageDomain, OrderOperateHistory orderOperateHistory) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<OrderOperateHistory> wrapper = new QueryWrapper<>(orderOperateHistory);
        // 2. 创建一个分页对象
        PageResult<OrderOperateHistory> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<OrderOperateHistory> orderOperateHistoryList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(orderOperateHistoryList);
    }

}
