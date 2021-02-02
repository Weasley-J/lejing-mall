package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.ware.dao.WareSkuDao;
import cn.alphahub.mall.ware.entity.WareSkuEntity;
import cn.alphahub.mall.ware.service.WareSkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

}