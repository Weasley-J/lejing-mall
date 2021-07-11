package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import cn.alphahub.mall.order.mapper.OrderReturnReasonMapper;
import cn.alphahub.mall.order.service.OrderReturnReasonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退货原因Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason> implements OrderReturnReasonService {

    /**
     * 查询退货原因分页列表
     *
     * @param pageDomain        分页数据
     * @param orderReturnReason 分页对象
     * @return 退货原因分页数据
     */
    @Override
    public PageResult<OrderReturnReason> queryPage(PageDomain pageDomain, OrderReturnReason orderReturnReason) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<OrderReturnReason> wrapper = new QueryWrapper<>(orderReturnReason);
        // 2. 创建一个分页对象
        PageResult<OrderReturnReason> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<OrderReturnReason> orderReturnReasonList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(orderReturnReasonList);
    }

}
