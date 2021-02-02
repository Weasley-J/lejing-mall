package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.order.dao.OrderReturnReasonDao;
import cn.alphahub.mall.order.entity.OrderReturnReasonEntity;
import cn.alphahub.mall.order.service.OrderReturnReasonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderReturnReasonService")
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonDao, OrderReturnReasonEntity> implements OrderReturnReasonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderReturnReasonEntity> page = this.page(
                new Query<OrderReturnReasonEntity>().getPage(params),
                new QueryWrapper<OrderReturnReasonEntity>()
        );

        return new PageUtils(page);
    }

}