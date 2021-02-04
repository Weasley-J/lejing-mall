package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SkuFullReductionMapper;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import cn.alphahub.mall.coupon.service.SkuFullReductionService;

import java.util.List;

/**
 * 商品满减信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReduction> implements SkuFullReductionService {

    /**
     * 查询商品满减信息分页列表
     *
     * @param pageDomain   分页数据
     * @param skuFullReduction 分页对象
     * @return 商品满减信息分页数据
     */
    @Override
    public PageResult<SkuFullReduction> queryPage(PageDomain pageDomain, SkuFullReduction skuFullReduction) {
        pageDomain.startPage();
        QueryWrapper<SkuFullReduction> wrapper = new QueryWrapper<>(skuFullReduction);
        List<SkuFullReduction> list = this.list(wrapper);
        PageInfo<SkuFullReduction> pageInfo = new PageInfo<>(list);
        PageResult<SkuFullReduction> pageResult = PageResult.<SkuFullReduction>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}