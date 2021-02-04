package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.MemberPriceMapper;
import cn.alphahub.mall.coupon.domain.MemberPrice;
import cn.alphahub.mall.coupon.service.MemberPriceService;

import java.util.List;

/**
 * 商品会员价格Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice> implements MemberPriceService {

    /**
     * 查询商品会员价格分页列表
     *
     * @param pageDomain   分页数据
     * @param memberPrice 分页对象
     * @return 商品会员价格分页数据
     */
    @Override
    public PageResult<MemberPrice> queryPage(PageDomain pageDomain, MemberPrice memberPrice) {
        pageDomain.startPage();
        QueryWrapper<MemberPrice> wrapper = new QueryWrapper<>(memberPrice);
        List<MemberPrice> list = this.list(wrapper);
        PageInfo<MemberPrice> pageInfo = new PageInfo<>(list);
        PageResult<MemberPrice> pageResult = PageResult.<MemberPrice>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}