package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderReturnReasonMapper;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.alphahub.mall.order.service.OrderReturnReasonService;

import java.util.List;

/**
 * 退货原因Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("orderReturnReasonService")
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason> implements OrderReturnReasonService {

    /**
     * 查询退货原因分页列表
     *
     * @param pageDomain   分页数据
     * @param orderReturnReason 分页对象
     * @return 退货原因分页数据
     */
    @Override
    public PageResult<OrderReturnReason> queryPage(PageDomain pageDomain, OrderReturnReason orderReturnReason) {
        pageDomain.startPage();
        QueryWrapper<OrderReturnReason> wrapper = new QueryWrapper<>(orderReturnReason);
        List<OrderReturnReason> list = this.list(wrapper);
        PageInfo<OrderReturnReason> pageInfo = new PageInfo<>(list);
        PageResult<OrderReturnReason> pageResult = PageResult.<OrderReturnReason>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}