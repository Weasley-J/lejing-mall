package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.dao.CouponDao;
import cn.alphahub.mall.coupon.entity.CouponEntity;
import cn.alphahub.mall.coupon.service.CouponService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwenjing
 */
@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {

    /**
     * 查询分页列表
     *
     * @param pageDomain 分页数据
     * @param domain     分页对象
     * @return 分页集合
     */
    @Override
    public PageResult<CouponEntity> queryPage(PageDomain pageDomain, CouponEntity domain) {
        pageDomain.startPage();
        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>(domain);
        List<CouponEntity> couponEntities = this.list(wrapper);
        PageInfo<CouponEntity> pageInfo = new PageInfo<>(couponEntities);
        PageResult<CouponEntity> pageResult = PageResult.<CouponEntity>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }
}