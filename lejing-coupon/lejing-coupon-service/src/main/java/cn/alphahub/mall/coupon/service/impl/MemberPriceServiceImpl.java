package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.MemberPrice;
import cn.alphahub.mall.coupon.mapper.MemberPriceMapper;
import cn.alphahub.mall.coupon.service.MemberPriceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品会员价格Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice> implements MemberPriceService {

    /**
     * 查询商品会员价格分页列表
     *
     * @param pageDomain  分页数据
     * @param memberPrice 分页对象
     * @return 商品会员价格分页数据
     */
    @Override
    public PageResult<MemberPrice> queryPage(PageDomain pageDomain, MemberPrice memberPrice) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberPrice> wrapper = new QueryWrapper<>(memberPrice);
        // 2. 创建一个分页对象
        PageResult<MemberPrice> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberPrice> memberPriceList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberPriceList);
    }

}
