package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.common.utils.Query;
import cn.alphahub.mall.product.dao.CommentReplayDao;
import cn.alphahub.mall.product.entity.CommentReplayEntity;
import cn.alphahub.mall.product.service.CommentReplayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("commentReplayService")
public class CommentReplayServiceImpl extends ServiceImpl<CommentReplayDao, CommentReplayEntity> implements CommentReplayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentReplayEntity> page = this.page(
                new Query<CommentReplayEntity>().getPage(params),
                new QueryWrapper<CommentReplayEntity>()
        );

        return new PageUtils(page);
    }

}