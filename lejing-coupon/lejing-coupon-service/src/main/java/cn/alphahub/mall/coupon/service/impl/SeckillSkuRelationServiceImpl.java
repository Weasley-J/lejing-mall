package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.coupon.mapper.SeckillSkuRelationMapper;
import cn.alphahub.mall.coupon.service.SeckillSkuRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀活动商品关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Service("seckillSkuRelationService")
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
        pageDomain.startPage();
        QueryWrapper<SeckillSkuRelation> wrapper = new QueryWrapper<>(seckillSkuRelation);
        List<SeckillSkuRelation> list = this.list(wrapper);
        PageInfo<SeckillSkuRelation> pageInfo = new PageInfo<>(list);
        PageResult<SeckillSkuRelation> pageResult = PageResult.<SeckillSkuRelation>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}
