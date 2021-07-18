package cn.alphahub.mall.product.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.service.BrandService;
import cn.hutool.core.lang.Console;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class OssClientTest {
    @Resource
    OssClient ossClient;
    @Resource
    BrandService brandService;
    @Resource
    ThreadPoolExecutor executor;

    @BeforeEach
    void setUp() {
        System.err.println("-----------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-----------------------");
    }

    @Test
    void createBucket() {
    }

    @Test
    void deleteBucket() {
    }

    /**
     * 转存品牌图片到oss
     */
    @Test
    void uploadSaveBrandToOss() {
        List<Brand> list = brandService.list();
        list.forEach(Console::log);
        if (CollectionUtils.isNotEmpty(list)) {
            Brand brand = list.get(0);
            BaseResult<String> upload = ossClient.upload(brand.getLogo(), AppConstant.LEJING_MALL);
            JSONUtil.printJsonStr(upload);
            if (StringUtils.isNotBlank(upload.getData())) {
                brandService.updateDetailById(Brand.builder()
                        .brandId(brand.getBrandId())
                        .logo(upload.getData())
                        .build());
                Brand byId = brandService.getById(brand.getBrandId());
                JSONUtil.printJsonStr(byId);
            }
        }
    }

    @Test
    void upload() {

    }

    @Test
    void testUpload() {
    }

    @Test
    void deleteSingle() {
    }

    @Test
    void deleteMany() {
    }

    @Test
    void isFileExist() {
    }

    @Test
    void isBucketExist() {
        BaseResult<Boolean> bucketExist = ossClient.isBucketExist("alphahub-test-bucket");
        JSONUtil.printJsonStr(bucketExist);
    }
}
