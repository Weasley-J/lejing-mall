package cn.alphahub.mall.thirdparty.oss;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.thirdparty.config.OssProperties;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@SpringBootTest
public class OSSFileUploadTest {

    @Resource
    private OSSClient ossClient;
    @Resource
    private OssProperties ossProperties;

    @BeforeEach
    void setUp() {
        log.info("--------------------------");
    }

    @AfterEach
    void tearDown() {
        log.info("--------------------------");
    }

    @Test
    void testOssUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "XXX";
        String accessKeySecret = "XXX";

        // 创建OSSClient实例。
        // OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("E:\\IdeaProjects\\lejing-mall\\lejing-product\\lejing-product-service\\src\\test\\java\\cn\\alphahub\\mall\\product\\oss\\OSSFileUploadTest.java");
        ossClient.putObject("alphahub-test-bucket", "OSSFileUploadTest.java", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传成功");
    }

    /**
     * 测试上传网络图片
     */
    @Test
    void testUploadWebImage() throws IOException {
        System.out.println(ossProperties);
        String imgUrl = "https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/ffc5a377-b037-4f26-84a0-df5b1c7cf42d_f205d9c99a2b4b01.jpg";
        String fileDir = AppConstant.LEJING_MALL + "/";
        String filename = StringUtils.substringAfterLast(imgUrl, "/");
        InputStream inputStream = new URL(imgUrl).openStream();
        PutObjectResult putObject = ossClient.putObject(ossProperties.getBucketName(), fileDir + filename, inputStream);
        ResponseMessage response = putObject.getResponse();
        System.out.println(response);
        ossClient.shutdown();
        System.out.println("上传成功");
    }
}
