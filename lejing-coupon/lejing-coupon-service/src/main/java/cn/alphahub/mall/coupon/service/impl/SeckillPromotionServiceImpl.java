package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillPromotion;
import cn.alphahub.mall.coupon.mapper.SeckillPromotionMapper;
import cn.alphahub.mall.coupon.service.SeckillPromotionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀活动Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SeckillPromotionServiceImpl extends ServiceImpl<SeckillPromotionMapper, SeckillPromotion> implements SeckillPromotionService {

    /**
     * 查询秒杀活动分页列表
     *
     * @param pageDomain       分页数据
     * @param seckillPromotion 分页对象
     * @return 秒杀活动分页数据
     */
    @Override
    public PageResult<SeckillPromotion> queryPage(PageDomain pageDomain, SeckillPromotion seckillPromotion) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SeckillPromotion> wrapper = new QueryWrapper<>(seckillPromotion);
        // 2. 创建一个分页对象
        PageResult<SeckillPromotion> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SeckillPromotion> seckillPromotionList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(seckillPromotionList);
    }

}
