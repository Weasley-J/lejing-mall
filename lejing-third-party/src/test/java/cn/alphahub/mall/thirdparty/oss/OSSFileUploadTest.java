package cn.alphahub.mall.thirdparty.oss;

import cn.alphahub.mall.thirdparty.config.OssProperties;
import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    void testUploadWebImage() {
    }
}
