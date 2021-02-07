package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import cn.alphahub.mall.coupon.mapper.CouponHistoryMapper;
import cn.alphahub.mall.coupon.service.CouponHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券领取历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Service("couponHistoryService")
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryMapper, CouponHistory> implements CouponHistoryService {

    /**
     * 查询优惠券领取历史记录分页列表
     *
     * @param pageDomain    分页数据
     * @param couponHistory 分页对象
     * @return 优惠券领取历史记录分页数据
     */
    @Override
    public PageResult<CouponHistory> queryPage(PageDomain pageDomain, CouponHistory couponHistory) {
        pageDomain.startPage();
        QueryWrapper<CouponHistory> wrapper = new QueryWrapper<>(couponHistory);
        List<CouponHistory> list = this.list(wrapper);
        PageInfo<CouponHistory> pageInfo = new PageInfo<>(list);
        PageResult<CouponHistory> pageResult = PageResult.<CouponHistory>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}
