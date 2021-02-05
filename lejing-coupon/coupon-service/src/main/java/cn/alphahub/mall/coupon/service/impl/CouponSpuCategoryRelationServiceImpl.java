package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.CouponSpuCategoryRelationMapper;
import cn.alphahub.mall.coupon.domain.CouponSpuCategoryRelation;
import cn.alphahub.mall.coupon.service.CouponSpuCategoryRelationService;

import java.util.List;

/**
 * 优惠券分类关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Service("couponSpuCategoryRelationService")
public class CouponSpuCategoryRelationServiceImpl extends ServiceImpl<CouponSpuCategoryRelationMapper, CouponSpuCategoryRelation> implements CouponSpuCategoryRelationService {

    /**
     * 查询优惠券分类关联分页列表
     *
     * @param pageDomain   分页数据
     * @param couponSpuCategoryRelation 分页对象
     * @return 优惠券分类关联分页数据
     */
    @Override
    public PageResult<CouponSpuCategoryRelation> queryPage(PageDomain pageDomain, CouponSpuCategoryRelation couponSpuCategoryRelation) {
        pageDomain.startPage();
        QueryWrapper<CouponSpuCategoryRelation> wrapper = new QueryWrapper<>(couponSpuCategoryRelation);
        List<CouponSpuCategoryRelation> list = this.list(wrapper);
        PageInfo<CouponSpuCategoryRelation> pageInfo = new PageInfo<>(list);
        PageResult<CouponSpuCategoryRelation> pageResult = PageResult.<CouponSpuCategoryRelation>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}