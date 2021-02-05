package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SeckillPromotionMapper;
import cn.alphahub.mall.coupon.domain.SeckillPromotion;
import cn.alphahub.mall.coupon.service.SeckillPromotionService;

import java.util.List;

/**
 * 秒杀活动Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Service("seckillPromotionService")
public class SeckillPromotionServiceImpl extends ServiceImpl<SeckillPromotionMapper, SeckillPromotion> implements SeckillPromotionService {

    /**
     * 查询秒杀活动分页列表
     *
     * @param pageDomain   分页数据
     * @param seckillPromotion 分页对象
     * @return 秒杀活动分页数据
     */
    @Override
    public PageResult<SeckillPromotion> queryPage(PageDomain pageDomain, SeckillPromotion seckillPromotion) {
        pageDomain.startPage();
        QueryWrapper<SeckillPromotion> wrapper = new QueryWrapper<>(seckillPromotion);
        List<SeckillPromotion> list = this.list(wrapper);
        PageInfo<SeckillPromotion> pageInfo = new PageInfo<>(list);
        PageResult<SeckillPromotion> pageResult = PageResult.<SeckillPromotion>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}