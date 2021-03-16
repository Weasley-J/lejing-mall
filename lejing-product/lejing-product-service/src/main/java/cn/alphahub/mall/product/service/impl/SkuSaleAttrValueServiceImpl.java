package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuSaleAttrValue;
import cn.alphahub.mall.product.mapper.SkuSaleAttrValueMapper;
import cn.alphahub.mall.product.service.SkuSaleAttrValueService;
import cn.alphahub.mall.product.vo.SkuItemSaleAttrVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sku销售属性&值Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> implements SkuSaleAttrValueService {

    /**
     * 查询sku销售属性&值分页列表
     *
     * @param pageDomain       分页数据
     * @param skuSaleAttrValue 分页对象
     * @return sku销售属性&值分页数据
     */
    @Override
    public PageResult<SkuSaleAttrValue> queryPage(PageDomain pageDomain, SkuSaleAttrValue skuSaleAttrValue) {
        QueryWrapper<SkuSaleAttrValue> wrapper = new QueryWrapper<>(skuSaleAttrValue);
        PageResult<SkuSaleAttrValue> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<SkuSaleAttrValue> skuSaleAttrValueList = this.list(wrapper);
        return pageResult.getPage(this.list(wrapper));
    }

    /**
     * 获取spu销售属性组合
     *
     * @param spuId 商品spuId
     * @return 销售属性组合
     */
    @Override
    public List<SkuItemSaleAttrVO> getSaleAttrBySpuId(Long spuId) {
        return this.baseMapper.getSaleAttrBySpuId(spuId);
    }

}
