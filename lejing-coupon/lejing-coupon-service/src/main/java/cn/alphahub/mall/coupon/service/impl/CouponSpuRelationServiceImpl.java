package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuRelation;
import cn.alphahub.mall.coupon.mapper.CouponSpuRelationMapper;
import cn.alphahub.mall.coupon.service.CouponSpuRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券与产品关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
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
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<CouponSpuRelation> wrapper = new QueryWrapper<>(couponSpuRelation);
        // 2. 创建一个分页对象
        PageResult<CouponSpuRelation> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<CouponSpuRelation> couponSpuRelationList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(couponSpuRelationList);
    }

}
