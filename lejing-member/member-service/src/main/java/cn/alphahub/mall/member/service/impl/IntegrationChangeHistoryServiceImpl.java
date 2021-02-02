package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.member.dao.IntegrationChangeHistoryDao;
import cn.alphahub.mall.member.entity.IntegrationChangeHistoryEntity;
import cn.alphahub.mall.member.service.IntegrationChangeHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("integrationChangeHistoryService")
public class IntegrationChangeHistoryServiceImpl extends ServiceImpl<IntegrationChangeHistoryDao, IntegrationChangeHistoryEntity> implements IntegrationChangeHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IntegrationChangeHistoryEntity> page = this.page(
                new Query<IntegrationChangeHistoryEntity>().getPage(params),
                new QueryWrapper<IntegrationChangeHistoryEntity>()
        );

        return new PageUtils(page);
    }

}