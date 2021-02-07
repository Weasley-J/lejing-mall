package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SkuSaleAttrValueMapper;
import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.service.SkuSaleAttrValueService;

import java.util.List;

/**
 * sku销售属性&值Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> implements SkuSaleAttrValueService {

    /**
     * 查询sku销售属性&值分页列表
     *
     * @param pageDomain   分页数据
     * @param skuSaleAttrValue 分页对象
     * @return sku销售属性&值分页数据
     */
    @Override
    public PageResult<SkuSaleAttrValue> queryPage(PageDomain pageDomain, SkuSaleAttrValue skuSaleAttrValue) {
        pageDomain.startPage();
        QueryWrapper<SkuSaleAttrValue> wrapper = new QueryWrapper<>(skuSaleAttrValue);
        List<SkuSaleAttrValue> list = this.list(wrapper);
        PageInfo<SkuSaleAttrValue> pageInfo = new PageInfo<>(list);
        PageResult<SkuSaleAttrValue> pageResult = PageResult.<SkuSaleAttrValue>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}