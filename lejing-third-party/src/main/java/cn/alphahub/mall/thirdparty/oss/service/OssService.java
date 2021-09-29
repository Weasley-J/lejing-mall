package cn.alphahub.mall.thirdparty.oss.service;

import cn.alphahub.common.util.URLUtil;
import cn.alphahub.mall.thirdparty.config.AliyunConfig;
import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
public class OssService {
    @Resource
    private OSS ossClient;
    @Resource
    private AliyunConfig.OssProperties ossProperties;

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间名称
     * @return 创建成功，返回存储空间名称
     */
    public String createBucket(String bucketName) {
        log.info("创建存储空间：{}", bucketName);
        return ossClient.createBucket(bucketName).getName();
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
     * 上传文件至OSS
     * <p>
     * 可以上传的文件类型：
     *     <ul>
     *         <li>上传本地文件到OSS时需要指定包含文件后缀在内的完整路径，例如: E:\IdeaProjects\lejing-mall\lejing-third-party\src\main\resources\application-dev.yml</li>
     *         <li>上传网络文件，如：https://lejing.com/1.jpg</li>
     *     </ul>
     * </p>
     *
     * @param objectName   对象名，可以是本地文件，可以是网络文件
     * @param fileDirOfOss 文件在oss bucket中的文件夹
     * @return 文件的url
     */
    public String upload(String objectName, String fileDirOfOss) {
        log.info("上传文件：{}", objectName);
        if (StringUtils.isBlank(objectName)) {
            return null;
        }
        String bucketName = ossProperties.getBucketName();
        if (!isBucketExist(bucketName)) {
            createBucket(bucketName);
        }
        fileDirOfOss = StringUtils.isBlank(fileDirOfOss) ? "" : fileDirOfOss + "/";
        PutObjectResult result = new PutObjectResult();
        String filename;
        if (URLUtil.isValidUrl(objectName)) {
            filename = StringUtils.substringAfterLast(objectName, "/");
            try (InputStream inputStream = new URL(objectName).openStream()) {
                result = ossClient.putObject(ossProperties.getBucketName(), fileDirOfOss + filename, inputStream);
            } catch (IOException e) {
                log.error("上传失败：{}", e.getMessage(), e);
            }
        } else {
            filename = FileUtil.getName(objectName);
            result = ossClient.putObject(bucketName, fileDirOfOss + filename, new File(objectName));
        }
        return ObjectUtils.isNotEmpty(result) ? ossProperties.getUrlPrefix() + fileDirOfOss + filename : null;
    }

    /**
     * 上传文件流
     * <p>以下代码用于上传文件流</p>
     *
     * @param inputStream 上传文件流
     * @param filename    文件名(包含后缀)
     * @return 文件完整url
     */
    public String upload(InputStream inputStream, String filename) {
        String bucketName = ossProperties.getBucketName();
        if (!isBucketExist(bucketName) || Objects.isNull(inputStream) || StringUtils.isBlank(filename)) {
            return null;
        }
        //组装文件名-uri路径，如: jpg/abc.jpg
        String myObjectName = makeOssPath(filename);
        PutObjectResult result = ossClient.putObject(bucketName, myObjectName, inputStream);
        log.info("结果：{}", result.toString());
        return ossProperties.getUrlPrefix().concat(myObjectName);
    }

    /**
     * 上传文件流
     *
     * @param file     multipart file
     * @param filename 文件名(包含后缀)
     * @return 文件完整url
     */
    public String upload(MultipartFile file, String filename) throws IOException {
        return upload(file.getInputStream(), filename);
    }

    /**
     * 删除单个文件
     *
     * @param objectName 文件URL,上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     *                   删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。
     *                   如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
     */
    public void deleteOne(String objectName) {
        String bucketName = ossProperties.getBucketName();
        ossClient.deleteObject(bucketName, objectName);
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件URL集合,上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
     *                    删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。
     *                    如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
     * @return 删除结果:详细模式下为删除成功的文件列表，简单模式下为删除失败的文件列表。
     */
    public List<String> deleteMany(List<String> objectNames) {
        String bucketName = ossProperties.getBucketName();
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(objectNames));
        return deleteObjectsResult.getDeletedObjects();
    }

    /**
     * OSS下载文件
     * <p><a href="https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-z">文档帮助链接</a></p>
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
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("IO异常: {}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param objectUrl 文件的完整url
     * @return 是否存在
     */
    public boolean isFileExist(String objectUrl) {
        log.info("文件的完整url:{}", objectUrl);
        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；
        String objectName = objectUrl.replace(ossProperties.getUrlPrefix(), "");
        return ossClient.doesObjectExist(ossProperties.getBucketName(), objectName);
    }

    /**
     * 判断OSS的全局命名空间(存储空间)是否存在
     *
     * @return 存在返回true，否则返回false
     */
    public boolean isBucketExist(String bucketName) {
        return ossClient.doesBucketExist(bucketName);
    }

    /**
     * 组装Oss文件uri路径，格式: jpg/abc.jpg
     *
     * @param filename 文件名
     * @return 文件uri
     */
    public String makeOssPath(String filename) {
        String fileType = StringUtils.substringAfterLast(filename, ".");
        filename = StringUtils.substringBeforeLast(filename, ".");
        filename = filename.replace(" ", "_");
        StringJoiner myObjectJoiner = new StringJoiner("/", "", "");
        myObjectJoiner.add(fileType).add(filename.concat(".").concat(fileType));
        return myObjectJoiner.toString();
    }
}

