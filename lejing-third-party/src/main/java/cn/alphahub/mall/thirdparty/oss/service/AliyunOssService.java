package cn.alphahub.mall.thirdparty.oss.service;

import cn.alphahub.mall.thirdparty.config.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 阿里云Oss实现类
 *
 * @author liuwenjing
 */
@Slf4j
@Service
public class AliyunOssService {
    @Resource
    private OSS ossClient;
    @Resource
    private OssProperties ossProperties;

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间名称
     * @return 创建成功，返回存储空间名称
     */
    public String createBucket(String bucketName) {
        log.info("创建存储空间：{}", bucketName);
        Bucket bucket = ossClient.createBucket(bucketName);
        return bucket.getName();
    }

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间名称
     */
    public void deleteBucket(String bucketName) {
        log.info("删除存储空间：{}", bucketName);
        if (ossClient.doesBucketExist(bucketName)) {
            ossClient.deleteBucket(bucketName);
        }
    }

    /**
     * 上传本地文件
     * <p>以下代码用于上传文件至OSS</p>
     *
     * @param objectName 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     * @return 文件完整url
     */
    public String upload(String objectName) {
        log.info("上传本地文件：{}", objectName);
        String bucketName = ossProperties.getBucketName();
        if (!isBucketExist(ossClient)) {
            this.createBucket(bucketName);
        }
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）
        String content = "Hello OSS";
        PutObjectResult result = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        return ObjectUtils.isNotEmpty(result) ? ossProperties.getUrlPrefix().concat(objectName) : null;
    }

    /**
     * 上传本地文件
     * <p>以下代码用于上传文件至OSS</p>
     *
     * @param file 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     * @return 文件完整url
     */

    public String upload(File file) {
        log.info("上传本地文件.");
        //从IOC容器中读取阿里云oss配置参数
        String bucketName = ossProperties.getBucketName();
        // 创建OSSClient实例。

        if (!isBucketExist(ossClient)) {
            return null;
        }
        //组装文件名-uri路径
        //如: jpg/20201014/abc_144845.jpg
        //1. 获取后缀
        String fileName = file.getName();
        String myObjectName = makeFileUri(fileName);
        PutObjectRequest request = new PutObjectRequest(bucketName, myObjectName, file);
        PutObjectResult result = ossClient.putObject(request);
        return ObjectUtils.isNotEmpty(result) ? ossProperties.getUrlPrefix().concat(myObjectName) : null;
    }

    /**
     * 上传文件流
     * <p>以下代码用于上传文件流</p>
     *
     * @param inputStream      上传文件流
     * @param originalFilename 文件名(包含后缀)
     * @return 文件完整url
     */

    public String upload(InputStream inputStream, String originalFilename) {
        //从IOC容器中读取阿里云oss配置参数
        String bucketName = ossProperties.getBucketName();
        //创建OSSClient实例。

        if (!isBucketExist(ossClient) || Objects.isNull(inputStream) || StringUtils.isBlank(originalFilename)) {
            return null;
        }
        //组装文件名-uri路径
        //如: jpg/20201014/abc_144845.jpg
        //1. 获取后缀
        String myObjectName = makeFileUri(originalFilename);
        //objectName 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        PutObjectResult result = ossClient.putObject(bucketName, myObjectName, inputStream);
        log.info("结果：{}", result.toString());
        ;
        return ossProperties.getHostPrefix().concat(myObjectName);
    }

    /**
     * 删除单个文件
     *
     * @param objectName 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     *                   删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。
     *                   如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
     */

    public void deleteSingle(String objectName) {
        String bucketName = ossProperties.getBucketName();
        ossClient.deleteObject(bucketName, objectName);
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     *                    删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。
     *                    如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
     * @return 删除结果:详细模式下为删除成功的文件列表，简单模式下为删除失败的文件列表。
     */

    public List<String> deleteMany(List<String> objectNames) {
        String bucketName = ossProperties.getBucketName();
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(objectNames));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        return deletedObjects;
    }

    /**
     * OSS下载文件
     * 文档帮助链接 https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-z
     *
     * @param os         输出流
     * @param objectName 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     */

    public void exportOssFile(OutputStream os, String objectName) {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), objectName);
        // 读取文件内容
        try (BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
             BufferedOutputStream out = new BufferedOutputStream(os)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("\nIO异常: {}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param objectName 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     * @return 是否存在
     */

    public boolean isFileExist(String objectName) {
        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；
        // 如果为false，则考虑302重定向或镜像。
        return ossClient.doesObjectExist(ossProperties.getBucketName(), objectName);
    }

    /**
     * 判断OSS的全局命名空间(存储空间)是否存在
     *
     * @param ossClient OSS对象
     * @return 存在，则返回true，否则返回false
     */
    public boolean isBucketExist(OSS ossClient) {
        String bucketName = ossProperties.getBucketName();
        return ossClient.doesBucketExist(bucketName);
    }

    /**
     * 组装文件名-uri路径
     * /*如: 20201014/jpg/abc.jpg
     *
     * @param filename 文件名
     * @return 文件uri
     */
    private String makeFileUri(String filename) {
        LocalDateTime now = LocalDateTime.now();
        String fileType = StringUtils.substringAfterLast(filename, ".");
        String dateDir = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        filename = StringUtils.substringBeforeLast(filename, ".");
        filename = filename.replace(" ", "_");
        //格式: 20201014/jpg/abc.jpg
        StringJoiner myObjectJoiner = new StringJoiner("/", "", "");
        myObjectJoiner.add(dateDir).add(fileType).add(filename.concat(".").concat(fileType));
        return myObjectJoiner.toString();
    }

    /**
     * 从oos url中获取bucket中的文件名
     *
     * @param url oss文件的url链接
     *            例如:
     *            传入: https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/pdf/20201014/阿里巴巴Java开发手册-2020最新嵩山版_155529.pdf
     *            返回: pdf/20201014/阿里巴巴Java开发手册-2020最新嵩山版_180902.pdf
     * @return bucket中的文件名
     */
    public String getOssObjectName(String url) {
        String urlPrefix = ossProperties.getHostPrefix() + "/";
        return url.replace(urlPrefix, "");
    }

    /**
     * 关闭OSS实例（释放所有资源） 调用其shutdown()后，OSS实例不可用
     *
     * @param ossClient OSS实例
     */
    public void shutdown(OSS ossClient) {
        if (Objects.nonNull(ossClient)) {
            ossClient.shutdown();
        }
    }
}

