package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SkuImagesMapper;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.service.SkuImagesService;

import java.util.List;

/**
 * sku图片Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImages> implements SkuImagesService {

    /**
     * 查询sku图片分页列表
     *
     * @param pageDomain   分页数据
     * @param skuImages 分页对象
     * @return sku图片分页数据
     */
    @Override
    public PageResult<SkuImages> queryPage(PageDomain pageDomain, SkuImages skuImages) {
        pageDomain.startPage();
        QueryWrapper<SkuImages> wrapper = new QueryWrapper<>(skuImages);
        List<SkuImages> list = this.list(wrapper);
        PageInfo<SkuImages> pageInfo = new PageInfo<>(list);
        PageResult<SkuImages> pageResult = PageResult.<SkuImages>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}