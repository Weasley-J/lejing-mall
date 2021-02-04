package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SkuLadderMapper;
import cn.alphahub.mall.coupon.domain.SkuLadder;
import cn.alphahub.mall.coupon.service.SkuLadderService;

import java.util.List;

/**
 * 商品阶梯价格Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("skuLadderService")
public class SkuLadderServiceImpl extends ServiceImpl<SkuLadderMapper, SkuLadder> implements SkuLadderService {

    /**
     * 查询商品阶梯价格分页列表
     *
     * @param pageDomain   分页数据
     * @param skuLadder 分页对象
     * @return 商品阶梯价格分页数据
     */
    @Override
    public PageResult<SkuLadder> queryPage(PageDomain pageDomain, SkuLadder skuLadder) {
        pageDomain.startPage();
        QueryWrapper<SkuLadder> wrapper = new QueryWrapper<>(skuLadder);
        List<SkuLadder> list = this.list(wrapper);
        PageInfo<SkuLadder> pageInfo = new PageInfo<>(list);
        PageResult<SkuLadder> pageResult = PageResult.<SkuLadder>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}