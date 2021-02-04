package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderMapper;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.service.OrderService;

import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 查询订单分页列表
     *
     * @param pageDomain   分页数据
     * @param order 分页对象
     * @return 订单分页数据
     */
    @Override
    public PageResult<Order> queryPage(PageDomain pageDomain, Order order) {
        pageDomain.startPage();
        QueryWrapper<Order> wrapper = new QueryWrapper<>(order);
        List<Order> list = this.list(wrapper);
        PageInfo<Order> pageInfo = new PageInfo<>(list);
        PageResult<Order> pageResult = PageResult.<Order>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}