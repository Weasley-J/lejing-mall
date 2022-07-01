package cn.alphahub.mall.product.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.domain.SpuImages;
import cn.alphahub.mall.product.service.BrandService;
import cn.alphahub.mall.product.service.SkuImagesService;
import cn.alphahub.mall.product.service.SkuInfoService;
import cn.alphahub.mall.product.service.SpuImagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class OssClientTest {
    @Resource
    OssClient ossClient;
    @Resource
    BrandService brandService;
    @Resource
    ThreadPoolExecutor executor;
    @Resource
    SkuImagesService skuImagesService;
    @Resource
    SkuInfoService skuInfoService;
    @Resource
    SpuImagesService spuImagesService;
    @Resource
    SearchClient searchClient;

    @BeforeEach
    void setUp() {
        System.err.println("-----------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-----------------------");
    }

    /**
     * 使用多线程批量转存品牌图片到oss
     */
    @Test
    void uploadSaveBrandToOss() {
        List<Brand> brands = brandService.list();
        if (CollectionUtils.isNotEmpty(brands)) {
            brands.forEach(brand1 -> {

                CompletableFuture<Brand> UrlFuture = CompletableFuture.supplyAsync(() -> {
                    Result<String> upload = ossClient.upload(brand1.getLogo(), AppConstant.LEJING_MALL);
                    brand1.setLogo(upload.getData());
                    return brand1;
                }, executor);

                CompletableFuture<Void> thenAcceptAsync = UrlFuture.thenAcceptAsync(brand2 -> {
                    if (StringUtils.isNotBlank(brand2.getLogo())) {
                        brandService.updateDetailById(Brand.builder()
                                .brandId(brand2.getBrandId())
                                .logo(brand2.getLogo())
                                .build()
                        );
                    }
                }, executor);

                /* 使用多线程异步任务编排 - 等待所有任务执行完才返回数据 */
                try {
                    CompletableFuture.allOf(UrlFuture, thenAcceptAsync).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 使用多线程批量转存sku图片到oss
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void saveSkuImgToOSS() throws ExecutionException, InterruptedException {
        List<SkuImages> images = skuImagesService.list(new QueryWrapper<SkuImages>().lambda()
                .select(SkuImages::getId, SkuImages::getImgUrl)
                .isNotNull(SkuImages::getImgUrl)
        ).stream().filter(skuImages ->
                StringUtils.isNotBlank(skuImages.getImgUrl()) && !skuImages.getImgUrl().toLowerCase().startsWith("https://alphahub")
        ).collect(Collectors.toList());

        List<SkuImages> skuImages = new ArrayList<>();
        for (SkuImages image : images) {
            CompletableFuture<SkuImages> UrlFuture = CompletableFuture.supplyAsync(() -> {
                Result<String> upload = ossClient.upload(image.getImgUrl(), AppConstant.LEJING_MALL);
                image.setImgUrl(upload.getData());
                return image;
            }, executor);
            CompletableFuture.allOf(UrlFuture).get();
            skuImages.add(UrlFuture.get());
        }
        skuImagesService.updateBatchById(skuImages);
    }

    /**
     * batch save sku default img to aliyun OSS
     */
    @Test
    void batchSaveSkuDefaultImgToOss() throws ExecutionException, InterruptedException {
        List<SkuInfo> newInfos = new ArrayList<>();

        List<SkuInfo> skuInfos = skuInfoService.list(new QueryWrapper<SkuInfo>().lambda()
                .select(SkuInfo::getSkuId, SkuInfo::getSkuDefaultImg)
                .isNotNull(SkuInfo::getSkuDefaultImg)
        ).stream().filter(skuInfo ->
                StringUtils.isNotBlank(skuInfo.getSkuDefaultImg()) && !skuInfo.getSkuDefaultImg().toLowerCase().startsWith("https://alphahub")
        ).collect(Collectors.toList());

        for (SkuInfo skuInfo : skuInfos) {
            String img = skuInfo.getSkuDefaultImg();
            CompletableFuture<SkuInfo> UrlFuture = CompletableFuture.supplyAsync(() -> {
                Result<String> upload = ossClient.upload(img, AppConstant.LEJING_MALL);
                skuInfo.setSkuDefaultImg(upload.getData());
                return skuInfo;
            }, executor);
            CompletableFuture.allOf(UrlFuture).get();
            newInfos.add(UrlFuture.get());
        }

        skuInfoService.updateBatchById(newInfos);
    }

    /**
     * batch save spu img to aliyun OSS
     */
    @Test
    void batchSaveSpuImgToOss() throws ExecutionException, InterruptedException {
        List<SpuImages> news = new ArrayList<>();
        List<SpuImages> spuImagesList = spuImagesService.list(new QueryWrapper<SpuImages>().lambda()
                .select(SpuImages::getId, SpuImages::getImgUrl)
                .isNotNull(SpuImages::getImgUrl)
        ).stream().filter(spuImages -> StringUtils.isNotBlank(spuImages.getImgUrl()) && !spuImages.getImgUrl().toLowerCase().startsWith("https://alphahub"))
                .collect(Collectors.toList());
        CompletableFuture<SpuImages> future = new CompletableFuture<>();
        for (SpuImages spuImages : spuImagesList) {
            future = CompletableFuture.supplyAsync(() -> {
                Result<String> upload = ossClient.upload(spuImages.getImgUrl(), AppConstant.LEJING_MALL);
                spuImages.setImgUrl(upload.getData());
                return spuImages;
            }, executor);
            news.add(future.get());
        }
        CompletableFuture.allOf(future).get();

        spuImagesService.updateBatchById(news);
    }


}
