package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderReturnApplyMapper;
import cn.alphahub.mall.order.domain.OrderReturnApply;
import cn.alphahub.mall.order.service.OrderReturnApplyService;

import java.util.List;

/**
 * 订单退货申请Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("orderReturnApplyService")
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

    /**
     * 查询订单退货申请分页列表
     *
     * @param pageDomain   分页数据
     * @param orderReturnApply 分页对象
     * @return 订单退货申请分页数据
     */
    @Override
    public PageResult<OrderReturnApply> queryPage(PageDomain pageDomain, OrderReturnApply orderReturnApply) {
        pageDomain.startPage();
        QueryWrapper<OrderReturnApply> wrapper = new QueryWrapper<>(orderReturnApply);
        List<OrderReturnApply> list = this.list(wrapper);
        PageInfo<OrderReturnApply> pageInfo = new PageInfo<>(list);
        PageResult<OrderReturnApply> pageResult = PageResult.<OrderReturnApply>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}