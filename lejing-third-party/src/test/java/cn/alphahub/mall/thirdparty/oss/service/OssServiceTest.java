package cn.alphahub.mall.thirdparty.oss.service;

import cn.alphahub.common.constant.AppConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OssServiceTest {
    @Resource
    private OssService ossService;

    @BeforeEach
    void setUp() {
        System.err.println("---------------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("---------------------------");
        ossService.shutdown();
    }

    @Test
    void createBucket() {
        ossService.createBucket(AppConstant.LEJING_MALL + "-001");
    }

    @Test
    void deleteBucket() {
        ossService.deleteBucket(AppConstant.LEJING_MALL + "-001");
    }

    /**
     * 上传本地文件到OSS
     */
    @Test
    void uploadLocalFile() {
        String objectName = "E:\\IdeaProjects\\lejing-mall\\lejing-third-party\\src\\main\\resources\\application-dev.yml";
        String fileDir = AppConstant.LEJING_MALL;
        String upload = ossService.upload(objectName, fileDir);
        System.out.println("文件连接：" + upload);
    }

    /**
     * 上传网络文件到OSS
     */
    @Test
    void uploadNetFile() {
        String objectName = "https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/ffc5a377-b037-4f26-84a0-df5b1c7cf42d_f205d9c99a2b4b01.jpg";
        String fileDir = AppConstant.LEJING_MALL;
        String upload = ossService.upload(objectName, fileDir);
        System.out.println("文件连接：" + upload);
    }

    @Test
    void testUpload() {
    }

    @Test
    void testUpload1() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void deleteMany() {
    }

    @Test
    void exportOssFile() {
    }

    @Test
    void isFileExist() {
    }

    @Test
    void isBucketExist() {
    }

    @Test
    void getOssObjectName() {
    }
}
