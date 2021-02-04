package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderItemMapper;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.service.OrderItemService;

import java.util.List;

/**
 * 订单项信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    /**
     * 查询订单项信息分页列表
     *
     * @param pageDomain   分页数据
     * @param orderItem 分页对象
     * @return 订单项信息分页数据
     */
    @Override
    public PageResult<OrderItem> queryPage(PageDomain pageDomain, OrderItem orderItem) {
        pageDomain.startPage();
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>(orderItem);
        List<OrderItem> list = this.list(wrapper);
        PageInfo<OrderItem> pageInfo = new PageInfo<>(list);
        PageResult<OrderItem> pageResult = PageResult.<OrderItem>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}