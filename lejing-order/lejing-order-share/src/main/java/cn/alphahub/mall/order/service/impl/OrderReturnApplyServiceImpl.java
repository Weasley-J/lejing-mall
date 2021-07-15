package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderReturnApply;
import cn.alphahub.mall.order.mapper.OrderReturnApplyMapper;
import cn.alphahub.mall.order.service.OrderReturnApplyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单退货申请Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

    /**
     * 查询订单退货申请分页列表
     *
     * @param pageDomain       分页数据
     * @param orderReturnApply 分页对象
     * @return 订单退货申请分页数据
     */
    @Override
    public PageResult<OrderReturnApply> queryPage(PageDomain pageDomain, OrderReturnApply orderReturnApply) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<OrderReturnApply> wrapper = new QueryWrapper<>(orderReturnApply);
        // 2. 创建一个分页对象
        PageResult<OrderReturnApply> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<OrderReturnApply> orderReturnApplyList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(orderReturnApplyList);
    }

}
