package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.order.dao.OrderOperateHistoryDao;
import cn.alphahub.mall.order.entity.OrderOperateHistoryEntity;
import cn.alphahub.mall.order.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderOperateHistoryService")
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryDao, OrderOperateHistoryEntity> implements OrderOperateHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderOperateHistoryEntity> page = this.page(
                new Query<OrderOperateHistoryEntity>().getPage(params),
                new QueryWrapper<OrderOperateHistoryEntity>()
        );

        return new PageUtils(page);
    }

}