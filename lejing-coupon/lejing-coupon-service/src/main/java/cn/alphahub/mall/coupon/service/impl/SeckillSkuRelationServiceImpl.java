package cn.alphahub.mall.coupon.service.impl;


import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.coupon.mapper.SeckillSkuRelationMapper;
import cn.alphahub.mall.coupon.service.SeckillSkuRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀活动商品关联Service业务层处理
 *
 * @author Weasley J
 * @date 2021-02-24 16:31:15
 */
@Service
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationMapper, SeckillSkuRelation> implements SeckillSkuRelationService {

    /**
     * 查询秒杀活动商品关联分页列表
     *
     * @param pageDomain         分页数据
     * @param seckillSkuRelation 分页对象
     * @return 秒杀活动商品关联分页数据
     */
    @Override
    public PageResult<SeckillSkuRelation> queryPage(PageDomain pageDomain, SeckillSkuRelation seckillSkuRelation) {
        QueryWrapper<SeckillSkuRelation> wrapper = new QueryWrapper<>(seckillSkuRelation);
        PageResult<SeckillSkuRelation> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<SeckillSkuRelation> seckillSkuRelationList = this.list(wrapper);
        return pageResult.getPage(seckillSkuRelationList);
    }
}
