package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.MemberPriceTo;
import cn.alphahub.common.to.SkuReductionTo;
import cn.alphahub.mall.coupon.domain.MemberPrice;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import cn.alphahub.mall.coupon.domain.SkuLadder;
import cn.alphahub.mall.coupon.mapper.SkuFullReductionMapper;
import cn.alphahub.mall.coupon.service.MemberPriceService;
import cn.alphahub.mall.coupon.service.SkuFullReductionService;
import cn.alphahub.mall.coupon.service.SkuLadderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品满减信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReduction> implements SkuFullReductionService {

    @Resource
    private SkuLadderService skuLadderService;
    @Resource
    private MemberPriceService memberPriceService;

    /**
     * 查询商品满减信息分页列表
     *
     * @param pageDomain       分页数据
     * @param skuFullReduction 分页对象
     * @return 商品满减信息分页数据
     */
    @Override
    public PageResult<SkuFullReduction> queryPage(PageDomain pageDomain, SkuFullReduction skuFullReduction) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SkuFullReduction> wrapper = new QueryWrapper<>(skuFullReduction);
        // 2. 创建一个分页对象
        PageResult<SkuFullReduction> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SkuFullReduction> skuFullReductionList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(skuFullReductionList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSkuReduction(SkuReductionTo reductionTO) {
        boolean b1 = false, b2 = false, b3 = false;
        // 1. 保存满减、打折、会员价
        //  5.4）、sku的优惠、满减等信息；lejing_sms -> sms_sku_ladder|sms_sku_full_reduction|sms_member_price
        // insert into sms_sku_ladder
        SkuLadder skuLadder = new SkuLadder();
        skuLadder.setSkuId(reductionTO.getSkuId());
        skuLadder.setFullCount(reductionTO.getFullCount());
        skuLadder.setDiscount(reductionTO.getDiscount());
        skuLadder.setAddOther(reductionTO.getCountStatus());
        if (skuLadder.getFullCount() > 0) {
            skuLadderService.save(skuLadder);
        }

        // insert into sms_sku_full_reduction
        SkuFullReduction reduction = new SkuFullReduction();
        BeanUtils.copyProperties(reductionTO, reduction);
        if (reduction.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            this.save(reduction);
        }

        // insert into sms_member_price
        List<MemberPriceTo> memberPriceList = reductionTO.getMemberPrice();
        if (CollectionUtils.isNotEmpty(memberPriceList)) {
            List<MemberPrice> memberPrices = memberPriceList.stream().map(item -> MemberPrice.builder()
                    .skuId(reductionTO.getSkuId())
                    .memberLevelId(item.getId())
                    .memberLevelName(item.getName())
                    .memberPrice(item.getPrice())
                    .addOther(1)
                    .build())
                    .filter(memberPrice -> memberPrice.getMemberPrice().compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toList());
            memberPriceService.saveBatch(memberPrices);
        }
        return true;
    }
}
