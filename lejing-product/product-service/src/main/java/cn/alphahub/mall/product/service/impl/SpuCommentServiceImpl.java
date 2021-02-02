package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.product.dao.SpuCommentDao;
import cn.alphahub.mall.product.entity.SpuCommentEntity;
import cn.alphahub.mall.product.service.SpuCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentDao, SpuCommentEntity> implements SpuCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuCommentEntity> page = this.page(
                new Query<SpuCommentEntity>().getPage(params),
                new QueryWrapper<SpuCommentEntity>()
        );

        return new PageUtils(page);
    }

}