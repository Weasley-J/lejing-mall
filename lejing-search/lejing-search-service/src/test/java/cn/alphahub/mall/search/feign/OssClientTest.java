package cn.alphahub.mall.search.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.search.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest
class OssClientTest {
    @Resource
    OssClient ossClient;
    @Resource
    ProductRepository repository;
    @Resource
    ElasticsearchRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        System.err.println("-----------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-----------------------");
    }

    /**
     * 批量转存图片资源到OSS
     */
    @Test
    void upload() {
        Iterable<SkuModel> all = repository.findAll();
        for (SkuModel skuModel : all) {
            String skuImg = skuModel.getSkuImg();
            Result<String> upload = ossClient.upload(skuImg, AppConstant.LEJING_MALL);
            if (StringUtils.isNotBlank(upload.getData())) {
                skuModel.setSkuImg(upload.getData());
            }
            String brandImg = skuModel.getBrandImg();
            upload = ossClient.upload(brandImg, AppConstant.LEJING_MALL);
            if (StringUtils.isNotBlank(upload.getData())) {
                skuModel.setBrandImg(upload.getData());
            }
        }
        repository.saveAll(all);
    }
}
