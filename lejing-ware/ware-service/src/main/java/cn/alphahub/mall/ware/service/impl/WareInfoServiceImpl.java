package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.ware.dao.WareInfoDao;
import cn.alphahub.mall.ware.entity.WareInfoEntity;
import cn.alphahub.mall.ware.service.WareInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>()
        );

        return new PageUtils(page);
    }

}