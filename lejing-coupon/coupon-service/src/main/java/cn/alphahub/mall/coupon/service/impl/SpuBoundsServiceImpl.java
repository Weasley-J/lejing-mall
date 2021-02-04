package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SpuBoundsMapper;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import cn.alphahub.mall.coupon.service.SpuBoundsService;

import java.util.List;

/**
 * 商品spu积分设置Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("spuBoundsService")
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsMapper, SpuBounds> implements SpuBoundsService {

    /**
     * 查询商品spu积分设置分页列表
     *
     * @param pageDomain   分页数据
     * @param spuBounds 分页对象
     * @return 商品spu积分设置分页数据
     */
    @Override
    public PageResult<SpuBounds> queryPage(PageDomain pageDomain, SpuBounds spuBounds) {
        pageDomain.startPage();
        QueryWrapper<SpuBounds> wrapper = new QueryWrapper<>(spuBounds);
        List<SpuBounds> list = this.list(wrapper);
        PageInfo<SpuBounds> pageInfo = new PageInfo<>(list);
        PageResult<SpuBounds> pageResult = PageResult.<SpuBounds>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}