package cn.alphahub.mall.reserve.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.order.domain.OrderReimburse;
import cn.alphahub.mall.reserve.order.mapper.OrderReimburseMapper;
import cn.alphahub.mall.reserve.order.service.OrderReimburseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单退款表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Service("orderReimburseService")
public class OrderReimburseServiceImpl extends ServiceImpl<OrderReimburseMapper, OrderReimburse> implements OrderReimburseService {

    /**
     * 查询订单退款表分页列表
     *
     * @param pageDomain     分页数据
     * @param orderReimburse 分页对象
     * @return 订单退款表分页数据
     */
    @Override
    public PageResult<OrderReimburse> queryPage(PageDomain pageDomain, OrderReimburse orderReimburse) {
        pageDomain.startPage();
        QueryWrapper<OrderReimburse> wrapper = new QueryWrapper<>(orderReimburse);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>订单退款表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<OrderReimburse> getPageResult(QueryWrapper<OrderReimburse> wrapper) {
        List<OrderReimburse> list = this.list(wrapper);
        PageInfo<OrderReimburse> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<OrderReimburse>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
