package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuRelation;
import cn.alphahub.mall.coupon.mapper.CouponSpuRelationMapper;
import cn.alphahub.mall.coupon.service.CouponSpuRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券与产品关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Service("couponSpuRelationService")
public class CouponSpuRelationServiceImpl extends ServiceImpl<CouponSpuRelationMapper, CouponSpuRelation> implements CouponSpuRelationService {

    /**
     * 查询优惠券与产品关联分页列表
     *
     * @param pageDomain        分页数据
     * @param couponSpuRelation 分页对象
     * @return 优惠券与产品关联分页数据
     */
    @Override
    public PageResult<CouponSpuRelation> queryPage(PageDomain pageDomain, CouponSpuRelation couponSpuRelation) {
        pageDomain.startPage();
        QueryWrapper<CouponSpuRelation> wrapper = new QueryWrapper<>(couponSpuRelation);
        List<CouponSpuRelation> list = this.list(wrapper);
        PageInfo<CouponSpuRelation> pageInfo = new PageInfo<>(list);
        PageResult<CouponSpuRelation> pageResult = PageResult.<CouponSpuRelation>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}
