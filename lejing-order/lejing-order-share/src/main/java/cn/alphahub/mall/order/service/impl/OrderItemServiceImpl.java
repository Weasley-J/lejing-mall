package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.mapper.OrderItemMapper;
import cn.alphahub.mall.order.service.OrderItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单项信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    /**
     * 查询订单项信息分页列表
     *
     * @param pageDomain 分页数据
     * @param orderItem  分页对象
     * @return 订单项信息分页数据
     */
    @Override
    public PageResult<OrderItem> queryPage(PageDomain pageDomain, OrderItem orderItem) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>(orderItem);
        // 2. 创建一个分页对象
        PageResult<OrderItem> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<OrderItem> orderItemList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(orderItemList);
    }

}
