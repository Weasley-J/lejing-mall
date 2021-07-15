package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import cn.alphahub.mall.coupon.mapper.CouponHistoryMapper;
import cn.alphahub.mall.coupon.service.CouponHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券领取历史记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
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
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<CouponHistory> wrapper = new QueryWrapper<>(couponHistory);
        // 2. 创建一个分页对象
        PageResult<CouponHistory> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<CouponHistory> couponHistoryList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(couponHistoryList);
    }

}
