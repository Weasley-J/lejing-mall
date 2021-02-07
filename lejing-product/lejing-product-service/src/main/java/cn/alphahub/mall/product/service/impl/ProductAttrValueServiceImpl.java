package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.ProductAttrValueMapper;
import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.service.ProductAttrValueService;

import java.util.List;

/**
 * spu属性值Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {

    /**
     * 查询spu属性值分页列表
     *
     * @param pageDomain   分页数据
     * @param productAttrValue 分页对象
     * @return spu属性值分页数据
     */
    @Override
    public PageResult<ProductAttrValue> queryPage(PageDomain pageDomain, ProductAttrValue productAttrValue) {
        pageDomain.startPage();
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>(productAttrValue);
        List<ProductAttrValue> list = this.list(wrapper);
        PageInfo<ProductAttrValue> pageInfo = new PageInfo<>(list);
        PageResult<ProductAttrValue> pageResult = PageResult.<ProductAttrValue>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}