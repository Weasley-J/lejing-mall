package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderStock;
import cn.alphahub.mall.order.mapper.OrderStockMapper;
import cn.alphahub.mall.order.service.OrderStockService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单库存表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Service("orderStockService")
public class OrderStockServiceImpl extends ServiceImpl<OrderStockMapper, OrderStock> implements OrderStockService {

    /**
     * 查询订单库存表分页列表
     *
     * @param pageDomain 分页数据
     * @param orderStock 分页对象
     * @return 订单库存表分页数据
     */
    @Override
    public PageResult<OrderStock> queryPage(PageDomain pageDomain, OrderStock orderStock) {
        pageDomain.startPage();
        QueryWrapper<OrderStock> wrapper = new QueryWrapper<>(orderStock);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>订单库存表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<OrderStock> getPageResult(QueryWrapper<OrderStock> wrapper) {
        List<OrderStock> list = this.list(wrapper);
        PageInfo<OrderStock> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<OrderStock>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
