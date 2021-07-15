package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.mapper.SkuImagesMapper;
import cn.alphahub.mall.product.service.SkuImagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sku图片Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImages> implements SkuImagesService {

    /**
     * 查询sku图片分页列表
     *
     * @param pageDomain 分页数据
     * @param skuImages  分页对象
     * @return sku图片分页数据
     */
    @Override
    public PageResult<SkuImages> queryPage(PageDomain pageDomain, SkuImages skuImages) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SkuImages> wrapper = new QueryWrapper<>(skuImages);
        // 2. 创建一个分页对象
        PageResult<SkuImages> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SkuImages> skuImagesList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(skuImagesList);
    }

    /**
     * 获取skuId的对应的图片列表
     *
     * @param skuId 商品skuId
     * @return 图片集合
     */
    @Override
    public List<SkuImages> getImagesBySkuId(Long skuId) {
        QueryWrapper<SkuImages> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SkuImages::getSkuId, skuId);
        return this.list(wrapper);
    }

}
