package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import cn.alphahub.mall.product.mapper.SkuInfoMapper;
import cn.alphahub.mall.product.service.AttrGroupService;
import cn.alphahub.mall.product.service.SkuImagesService;
import cn.alphahub.mall.product.service.SkuInfoService;
import cn.alphahub.mall.product.service.SkuSaleAttrValueService;
import cn.alphahub.mall.product.service.SpuInfoDescService;
import cn.alphahub.mall.product.vo.SeckillSkuVO;
import cn.alphahub.mall.product.vo.SkuItemSaleAttrVO;
import cn.alphahub.mall.product.vo.SkuItemVO;
import cn.alphahub.mall.product.vo.SpuItemAttrGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * sku信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Slf4j
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Resource
    private SkuImagesService imagesService;
    @Resource
    private SpuInfoDescService spuInfoDescService;
    @Resource
    private AttrGroupService attrGroupService;
    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;
    /**
     * 注入我们配置的线程池
     */
    @Resource
    private ThreadPoolExecutor executor;

    /**
     * 查询sku信息分页列表
     *
     * @param pageDomain 分页数据
     * @param skuInfo    分页对象
     * @return sku信息分页数据
     */
    @Override
    public PageResult<SkuInfo> queryPage(PageDomain pageDomain, SkuInfo skuInfo) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>(skuInfo);
        // 2. 创建一个分页对象
        PageResult<SkuInfo> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SkuInfo> skuInfoList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(skuInfoList);
    }

    @Override
    public PageResult<SkuInfo> queryPage(PageDomain pageDomain, String key, Long catelogId, Long brandId, Long min, Long max) {
        log.info("参数列表:key->{},catelogId->{},brandId->{},min->{},max->{}", key, catelogId, brandId, min, max);
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            wrapper.lambda().and(w -> w.eq(SkuInfo::getSkuId, key).or().like(SkuInfo::getSkuName, key));
        }
        if (ObjectUtils.isNotEmpty(catelogId) && catelogId != 0) {
            wrapper.lambda().eq(SkuInfo::getCatalogId, catelogId);
        }
        if (ObjectUtils.isNotEmpty(brandId) && brandId != 0) {
            wrapper.lambda().eq(SkuInfo::getBrandId, brandId);
        }

        if (ObjectUtils.isNotEmpty(min)) {
            try {
                if (new BigDecimal(min).compareTo(BigDecimal.ZERO) > 0) {
                    wrapper.lambda().ge(SkuInfo::getPrice, min);
                }
            } catch (Exception e) {
                log.error("输入min数值不规范:{},{}", e.getMessage(), e);
            }
        }

        if (ObjectUtils.isNotEmpty(max)) {
            try {
                if (new BigDecimal(max).compareTo(BigDecimal.ZERO) > 0) {
                    wrapper.lambda().le(SkuInfo::getPrice, max);
                }
            } catch (Exception e) {
                log.error("输入max数值不规范:{},{}", e.getMessage(), e);
            }
        }

        PageResult<SkuInfo> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<SkuInfo> skuInfoList = this.list(wrapper);
        return pageResult.getPage(skuInfoList);
    }

    /**
     * 根据spuId查出对应的所有sku信息，品牌名字
     *
     * @param spuId spuId
     * @return 所有sku信息
     */
    @Override
    public List<SkuInfo> getSkusBySpuId(Long spuId) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();
        return this.list(queryWrapper.lambda().eq(SkuInfo::getSpuId, spuId));
    }

    /**
     * 根据skuId获取商品详情
     *
     * @param skuId 商品skuId
     * @return 商品详情页数据
     */
    @Override
    public SkuItemVO getSkuItemBySkuId(Long skuId) {

        // skuId直接返回空数据,不用去查库了
        if (ObjectUtils.allNull(skuId) || Objects.equals(skuId, 404L)) {
            return null;
        }

        // 创建一个商品详情页-VO对象，用于数据封装
        SkuItemVO itemVO = new SkuItemVO();

        /*
          使用CompletableFuture开启多线程任务 - 查询商品详情页数据
         */

        // 1. 开始异步线程编排: 查sku信息：pms_sku_info
        CompletableFuture<SkuInfo> skuFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = getById(skuId);
            // set sku基本信息
            itemVO.setInfo(skuInfo);
            return skuInfo;
        }, executor);

        // 2. 查询sku信息信息完成后  --> 获取spu销售属性组合
        CompletableFuture<Void> saleAttrFuture = skuFuture.thenAcceptAsync(skuInfo -> {
            List<SkuItemSaleAttrVO> saleAttr = skuSaleAttrValueService.getSaleAttrBySpuId(skuInfo.getSpuId());
            itemVO.setSaleAttr(saleAttr);
        }, executor);

        // 3. 查询sku信息信息完成后  --> 获取spu的介绍信息
        CompletableFuture<Void> spuDescFuture = skuFuture.thenAcceptAsync(skuInfo -> {
            Long spuId = skuInfo.getSpuId();
            SpuInfoDesc spuInfoDesc = spuInfoDescService.getById(spuId);
            itemVO.setDesc(spuInfoDesc);
        }, executor);

        // 4. 查询sku信息信息完成后  --> 获取商品sku属性组(获取包装规格参数信息)
        CompletableFuture<Void> attrGroupFuture = skuFuture.thenAcceptAsync(skuInfo -> {
            // 三级分类id
            Long catalogId = skuInfo.getCatalogId();
            // 商品spuId
            Long spuId = skuInfo.getSpuId();
            List<SpuItemAttrGroupVO> attrGroupVos = attrGroupService.listBySpuIdAndCatalogId(spuId, catalogId);
            itemVO.setGroupAttrs(attrGroupVos);
        }, executor);

        // 5. 使用CompletableFuture新开一个任务查询sku图片列表: pms_sku_images
        CompletableFuture<Void> skuImagesFuture = CompletableFuture.runAsync(() -> {
            List<SkuImages> skuImages = imagesService.getImagesBySkuId(skuId);
            itemVO.setImages(skuImages);
        }, executor);

        /* 使用多线程异步任务编排 - 等待所有任务执行完才返回数据 */
        try {
            CompletableFuture.allOf(saleAttrFuture, spuDescFuture, attrGroupFuture, skuImagesFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("所有多线程异步任务执行任务出错，异常原因：{}", e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        }

        // 数据返回
        itemVO.setHasStock(true);
        itemVO.setSeckillSkuVo(new SeckillSkuVO());
        return itemVO;
    }

}
