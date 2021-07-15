package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuCategoryRelation;
import cn.alphahub.mall.coupon.mapper.CouponSpuCategoryRelationMapper;
import cn.alphahub.mall.coupon.service.CouponSpuCategoryRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券分类关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class CouponSpuCategoryRelationServiceImpl extends ServiceImpl<CouponSpuCategoryRelationMapper, CouponSpuCategoryRelation> implements CouponSpuCategoryRelationService {

    /**
     * 查询优惠券分类关联分页列表
     *
     * @param pageDomain                分页数据
     * @param couponSpuCategoryRelation 分页对象
     * @return 优惠券分类关联分页数据
     */
    @Override
    public PageResult<CouponSpuCategoryRelation> queryPage(PageDomain pageDomain, CouponSpuCategoryRelation couponSpuCategoryRelation) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<CouponSpuCategoryRelation> wrapper = new QueryWrapper<>(couponSpuCategoryRelation);
        // 2. 创建一个分页对象
        PageResult<CouponSpuCategoryRelation> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<CouponSpuCategoryRelation> couponSpuCategoryRelationList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(couponSpuCategoryRelationList);
    }

}
