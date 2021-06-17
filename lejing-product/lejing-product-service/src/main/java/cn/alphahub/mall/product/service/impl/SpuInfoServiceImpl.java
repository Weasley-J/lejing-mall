package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.constant.ProductConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.MemberPriceTo;
import cn.alphahub.common.to.SkuReductionTo;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import cn.alphahub.mall.product.domain.*;
import cn.alphahub.mall.product.feign.SearchClient;
import cn.alphahub.mall.product.feign.SkuFullReductionClient;
import cn.alphahub.mall.product.feign.SpuBoundsClient;
import cn.alphahub.mall.product.feign.WareSkuClient;
import cn.alphahub.mall.product.mapper.SpuInfoMapper;
import cn.alphahub.mall.product.service.*;
import cn.alphahub.mall.product.vo.BaseAttrs;
import cn.alphahub.mall.product.vo.Bounds;
import cn.alphahub.mall.product.vo.Images;
import cn.alphahub.mall.product.vo.Skus;
import cn.alphahub.mall.product.vo.SpuSaveVO;
import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * spu信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Slf4j
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

    @Resource
    private SearchClient searchClient;
    @Resource
    private SpuInfoDescService spuInfoDescService;
    @Resource
    private SpuImagesService spuImagesService;
    @Resource
    private AttrService attrService;
    @Resource
    private ProductAttrValueService productAttrValueService;
    @Resource
    private SkuInfoService skuInfoService;
    @Resource
    private SkuImagesService skuImagesService;
    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Resource
    private SpuBoundsClient spuBoundsClient;
    @Resource
    private SkuFullReductionClient skuFullReductionClient;
    @Resource
    private WareSkuClient wareSkuClient;
    @Resource
    private BrandService brandService;
    @Resource
    private CategoryService categoryService;

    /**
     * 查询spu信息分页列表
     *
     * @param pageDomain 分页数据
     * @param spuInfo    分页对象
     * @return spu信息分页数据
     */
    @Override
    public PageResult<SpuInfo> queryPage(PageDomain pageDomain, SpuInfo spuInfo) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>(spuInfo);
        // 2. 创建一个分页对象
        PageResult<SpuInfo> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SpuInfo> spuInfoList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(spuInfoList);

    }

    /**
     * 查询spu信息列表
     *
     * @param spuInfo   spu信息,查询字段选择性传入,默认为等值查询
     * @param key       检索关键字
     * @param catelogId 三级分类id
     * @param brandId   品牌id
     * @param status    商品状态
     * @return spu信息列表分页数据
     */
    @Override
    public PageResult<SpuInfo> queryPage(PageDomain pageDomain, SpuInfo spuInfo, String key, Integer catelogId, Integer brandId, Integer status) {
        log.info("参数:key-{},catalogId-{},brandId-{},status-{}", key, catelogId, brandId, status);
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>(spuInfo);
        // 检索关键字不为空
        if (StringUtils.isNotBlank(key)) {
            wrapper.lambda().and(w -> w.eq(SpuInfo::getId, key).or().like(SpuInfo::getSpuName, key));
        }
        // 商品状态
        if (ObjectUtils.isNotEmpty(status)) {
            wrapper.lambda().eq(SpuInfo::getPublishStatus, status);
        }
        // 品牌id
        if (ObjectUtils.isNotEmpty(brandId) &&
                !StringUtils.equals(ProductConstant.AttrEnum.SALE.getCode().toString(), String.valueOf(brandId))
        ) {
            wrapper.lambda().eq(SpuInfo::getBrandId, brandId);
        }
        // 三级分类id
        if (ObjectUtils.isNotEmpty(catelogId) &&
                !StringUtils.equals(ProductConstant.AttrEnum.SALE.getCode().toString(), String.valueOf(catelogId))
        ) {
            wrapper.lambda().eq(SpuInfo::getCatalogId, catelogId);
        }
        PageResult<SpuInfo> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<SpuInfo> spuInfoList = this.list(wrapper);
        return pageResult.getPage(spuInfoList);
    }

    /**
     * 上架商品
     *
     * @param spuId 商品spu id
     * @return 成功返回true, 失败返回false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean spuOnShelves(Long spuId) {
        // 组装要上架的商品信息
        List<SkuInfo> skuInfoList = skuInfoService.getSkusBySpuId(spuId);
        // 查询当前spu的所有规格属性（可以被检索）
        List<ProductAttrValue> oldProductAttrValues = productAttrValueService.listSpuBySpuId(spuId);
        List<Long> attrValueIdList = oldProductAttrValues.stream().map(ProductAttrValue::getAttrId).collect(Collectors.toList());

        List<Long> searchAttrIds = attrService.querySearchAttrIds(attrValueIdList);
        Set<Long> idSet = new LinkedHashSet<>(searchAttrIds);

        List<SkuModel.Attrs> attrsList = oldProductAttrValues.stream()
                .filter(productAttrValue -> idSet.contains(productAttrValue.getAttrId()))
                .map(productAttrValue -> {
                    SkuModel.Attrs attrs = new SkuModel.Attrs();
                    BeanUtils.copyProperties(productAttrValue, attrs);
                    return attrs;
                }).collect(Collectors.toList());

        // TODO 1.查看是否有库存
        List<Long> skuIds = skuInfoList.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());
        Map<Long, Boolean> stockMap = new LinkedHashMap<>();
        try {
            BaseResult<List<WareSkuVO>> baseResult = wareSkuClient.getSkuHasStock(skuIds);
            if (baseResult.getSuccess() && CollectionUtils.isNotEmpty(baseResult.getData())) {
                stockMap = baseResult.getData().stream().collect(Collectors.toMap(WareSkuVO::getSkuId, WareSkuVO::getHasStock));
            }
        } catch (Exception e) {
            log.error("远程查询库存失败：{}\n", e.getClass(), e);
            e.printStackTrace();
        }
        // 组装要上架的商品列表
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuModel> productOnShelvesList = skuInfoList.stream().map(sku -> {
            SkuModel skuModel = new SkuModel();
            // 根据spuId查出对应的所有sku信息，品牌名字
            BeanUtils.copyProperties(sku, skuModel);

            // 处理属性值不一样的数据
            skuModel.setSkuPrice(sku.getPrice());
            skuModel.setSkuImg(sku.getSkuDefaultImg());

            // 设置库存信息
            skuModel.setHasStock(CollectionUtils.isNotEmpty(finalStockMap) ? finalStockMap.get(sku.getSkuId()) : false);

            // TODO 2.feign远程调用库存服务查询热度评分
            skuModel.setHotScore(0L);

            // 品牌相关信息
            Brand brand = brandService.getById(sku.getBrandId());
            skuModel.setBrandId(sku.getBrandId());
            skuModel.setBrandName(Objects.nonNull(brand) ? brand.getName() : null);
            skuModel.setBrandImg(Objects.nonNull(brand) ? brand.getLogo() : null);

            // 查分类信息
            Category category = categoryService.getById(sku.getCatalogId());
            skuModel.setCatalogId(sku.getCatalogId());
            skuModel.setCatalogName(Objects.nonNull(category) ? category.getName() : null);

            // 设置检索属性
            skuModel.setAttrs(attrsList);
            return skuModel;
        }).collect(Collectors.toList());

        // TODO 把组装好要上架的商品信息发送给搜索服务: lejing-search
        BaseResult<Boolean> result = searchClient.productStatusUp(productOnShelvesList);
        String message = result.getMessage();
        log.info("\n\t\t上架的商品信息发送给搜索服务的结果：{}\n", message);
        // 上架成功，修改商品状态
        updateSpuStatusAfterSaveToElasticsearch(result, spuId);
        return !productOnShelvesList.isEmpty();
    }

    /**
     * 上架成功，修改商品状态
     *
     * @param result 远程调用返回的封装结果
     * @param spuId  spu id
     */
    private void updateSpuStatusAfterSaveToElasticsearch(BaseResult<Boolean> result, Long spuId) {
        SpuInfo spuInfo = this.getById(spuId);
        if (result.getSuccess()) {
            // 上架成功
            spuInfo.setPublishStatus(ProductConstant.StatusEnum.SPU_UP.getCode());
        } else {
            // 上架失败
            //TODO 接口幂等性，重试机制
            // spuInfo.setPublishStatus(ProductConstant.StatusEnum.NEW_SPU.getCode());
        }
        spuInfo.setUpdateTime(new Date());
        this.updateById(spuInfo);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfo(SpuSaveVO vo) {

        // 1、保存spu基本信息 pms_spu_info
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(vo, spuInfo);
        Date currTime = new Date();
        spuInfo.setCreateTime(currTime);
        // 2、保存Spu的描述图片 pms_spu_info_desc
        baseMapper.insert(spuInfo);

        List<String> decryptList = vo.getDecript();
        Long spuInfoId = spuInfo.getId();
        SpuInfoDesc spuInfoDesc = SpuInfoDesc.builder()
                .spuId(spuInfoId)
                .decript(String.join(",", decryptList))
                .build();
        log.info("spu信息介绍: {}\n", spuInfoDesc);
        spuInfoDescService.saveSpuInfoDesc(spuInfoDesc);
        // 3、保存spu的图片集 pms_spu_image
        List<String> images = vo.getImages();
        spuImagesService.saveBatch(spuInfoId, images);

        // 4、保存spu的规格参数;pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValue> productAttrValues = baseAttrs.stream().map(
                attr -> {
                    Attr byId = attrService.getById(attr.getAttrId());
                    return ProductAttrValue.builder()
                            .attrId(attr.getAttrId())
                            .attrName(Objects.nonNull(byId) ? byId.getAttrName() : "")
                            .attrValue(attr.getAttrValues())
                            .quickShow(attr.getShowDesc())
                            .spuId(spuInfoId)
                            .build();
                }
        ).collect(Collectors.toList());
        productAttrValueService.saveProductAttrValues(productAttrValues);

        // 5、保存spu的积分信息；lejing_sms -> sms_spu_bounds
        // 5、保存当前spu对应的所有sku信息:
        List<Skus> skusList = vo.getSkus();
        if (CollectionUtils.isNotEmpty(skusList)) {

            skusList.forEach(sku -> {

                String defaultImg = "";
                for (Images img : sku.getImages()) {
                    if (Objects.equals(img.getDefaultImg(), 1)) {
                        defaultImg = img.getImgUrl();
                    }
                }

                SkuInfo skuInfo = SkuInfo.builder().build();
                BeanUtils.copyProperties(sku, skuInfo);
                skuInfo.setSpuId(spuInfo.getId());
                skuInfo.setCatalogId(spuInfo.getCatalogId());
                skuInfo.setBrandId(spuInfo.getBrandId());
                skuInfo.setSkuDefaultImg(defaultImg);
                skuInfo.setSaleCount(0L);

                // 5.1）、sku的基本信息；pms_sku_info
                skuInfoService.save(skuInfo);

                Long skuId = skuInfo.getSkuId();
                List<Images> skuImages = sku.getImages();
                List<SkuImages> skuImagesList = skuImages.stream().map(img -> SkuImages.builder()
                        .skuId(skuId)
                        .defaultImg(img.getDefaultImg())
                        .imgUrl(img.getImgUrl())
                        .build())
                        .filter(img -> StringUtils.isNotBlank(img.getImgUrl()))
                        .collect(Collectors.toList());
                // 5.2）、sku的图片信息；pms_sku_image
                skuImagesService.saveBatch(skuImagesList);

                // 5.3）、sku的销售属性信息：pms_sku_sale_attr_value
                List<cn.alphahub.mall.product.vo.Attr> attr = sku.getAttr();
                List<SkuSaleAttrValue> skuSaleAttrValues = attr.stream().map(a -> {
                    SkuSaleAttrValue saleAttrValue = new SkuSaleAttrValue();
                    BeanUtils.copyProperties(a, saleAttrValue);
                    saleAttrValue.setSkuId(skuId);
                    return saleAttrValue;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValues);

                // 5.4）、sku的优惠、满减等信息；lejing_sms -> sms_spu_bounds|sms_sku_ladder|sms_sku_full_reduction|sms_member_price
                Bounds bounds = vo.getBounds();
                SpuBounds spuBounds = new SpuBounds();
                BeanUtils.copyProperties(bounds, spuBounds);
                spuBounds.setSpuId(spuInfoId);
                BaseResult<Boolean> save = spuBoundsClient.save(spuBounds);
                if (save.getSuccess()) {
                    log.info("{}", "远程保存商品spu积分成功");
                } else {
                    log.warn("{}", "远程保存商品spu积分失败");
                }
                //将前端提交的价格转换为TO对象
                List<MemberPriceTo> memberPriceToList = sku.getMemberPrice().stream().map(memberPrice -> {
                    MemberPriceTo memberPriceTo = new MemberPriceTo();
                    BeanUtils.copyProperties(memberPrice, memberPriceTo);
                    return memberPriceTo;
                }).collect(Collectors.toList());
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                skuReductionTo.setSkuId(skuId);
                skuReductionTo.setMemberPrice(memberPriceToList);
                BeanUtils.copyProperties(vo, skuReductionTo);
                Boolean b1 = Objects.nonNull(skuReductionTo.getFullCount()) && skuReductionTo.getFullCount() > 0;
                Boolean b2 = Objects.nonNull(skuReductionTo.getFullPrice()) && skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0;
                if (b1 || b2) {
                    BaseResult<Boolean> baseResult = skuFullReductionClient.saveSkuReduction(skuReductionTo);
                    log.info("保存结果：{}", JSONUtil.toJsonStr(baseResult));
                }
            });
        }
    }

}
