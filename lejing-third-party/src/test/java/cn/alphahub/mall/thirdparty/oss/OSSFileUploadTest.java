package cn.alphahub.mall.thirdparty.oss;

import cn.alphahub.mall.thirdparty.config.AliyunConfig;
import cn.alphahub.mall.thirdparty.oss.service.OssService;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
@SpringBootTest
public class OSSFileUploadTest {

    @Resource
    private OssService ossService;
    @Resource
    private AliyunConfig.OssProperties ossProperties;

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
        String endpoint = ossProperties.getEndpoint();
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ossProperties.getAccessKey();
        String accessKeySecret = ossProperties.getSecretKey();
        // 上传文件流。
        String name = "E:\\IdeaProjects\\lejing-mall\\lejing-third-party\\src\\test\\java\\cn\\alphahub\\mall\\thirdparty\\oss\\OSSFileUploadTest.java";
        InputStream inputStream = new FileInputStream(name);
        String upload = ossService.upload(inputStream, FileUtil.getName(name));
        System.out.println("上传成功:{}" + upload);
    }

    /**
     * 测试上传网络图片
     */
    @Test
    void makeOssPath() {
        String name = "E:\\IdeaProjects\\lejing-mall\\lejing-third-party\\src\\test\\java\\cn\\alphahub\\mall\\thirdparty\\oss\\OSSFileUploadTest.java";
        String urlPrefix = ossProperties.getUrlPrefix();
        String path = ossService.makeOssPath(FileUtil.getName(name));
        System.out.println("hostPrefix = " + urlPrefix);
        System.out.println("path = " + path);
    }
}
