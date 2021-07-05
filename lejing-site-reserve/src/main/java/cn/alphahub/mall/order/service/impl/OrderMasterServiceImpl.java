package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderMaster;
import cn.alphahub.mall.order.mapper.OrderMasterMapper;
import cn.alphahub.mall.order.service.OrderMasterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主订单表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Service("orderMasterService")
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {

    /**
     * 查询主订单表分页列表
     *
     * @param pageDomain  分页数据
     * @param orderMaster 分页对象
     * @return 主订单表分页数据
     */
    @Override
    public PageResult<OrderMaster> queryPage(PageDomain pageDomain, OrderMaster orderMaster) {
        pageDomain.startPage();
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>(orderMaster);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>主订单表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<OrderMaster> getPageResult(QueryWrapper<OrderMaster> wrapper) {
        List<OrderMaster> list = this.list(wrapper);
        PageInfo<OrderMaster> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<OrderMaster>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
