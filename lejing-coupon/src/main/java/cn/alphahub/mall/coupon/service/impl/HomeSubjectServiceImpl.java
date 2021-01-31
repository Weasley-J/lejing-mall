package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.common.utils.Query;
import cn.alphahub.mall.coupon.dao.HomeSubjectDao;
import cn.alphahub.mall.coupon.entity.HomeSubjectEntity;
import cn.alphahub.mall.coupon.service.HomeSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("homeSubjectService")
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectDao, HomeSubjectEntity> implements HomeSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeSubjectEntity> page = this.page(
                new Query<HomeSubjectEntity>().getPage(params),
                new QueryWrapper<HomeSubjectEntity>()
        );

        return new PageUtils(page);
    }

}