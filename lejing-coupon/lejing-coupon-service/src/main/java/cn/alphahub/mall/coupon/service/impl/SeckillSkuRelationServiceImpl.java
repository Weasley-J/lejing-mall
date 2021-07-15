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
 * @email 1432689025@qq.com
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
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SeckillSkuRelation> wrapper = new QueryWrapper<>(seckillSkuRelation);
        // 2. 创建一个分页对象
        PageResult<SeckillSkuRelation> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SeckillSkuRelation> seckillSkuRelationList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(seckillSkuRelationList);
    }

}
