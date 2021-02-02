package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.order.dao.UndoLogDao;
import cn.alphahub.mall.order.entity.UndoLogEntity;
import cn.alphahub.mall.order.service.UndoLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("undoLogService")
public class UndoLogServiceImpl extends ServiceImpl<UndoLogDao, UndoLogEntity> implements UndoLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UndoLogEntity> page = this.page(
                new Query<UndoLogEntity>().getPage(params),
                new QueryWrapper<UndoLogEntity>()
        );

        return new PageUtils(page);
    }

}