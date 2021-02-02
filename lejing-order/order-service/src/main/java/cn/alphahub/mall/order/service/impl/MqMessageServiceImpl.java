package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.order.dao.MqMessageDao;
import cn.alphahub.mall.order.entity.MqMessageEntity;
import cn.alphahub.mall.order.service.MqMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("mqMessageService")
public class MqMessageServiceImpl extends ServiceImpl<MqMessageDao, MqMessageEntity> implements MqMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MqMessageEntity> page = this.page(
                new Query<MqMessageEntity>().getPage(params),
                new QueryWrapper<MqMessageEntity>()
        );

        return new PageUtils(page);
    }

}