package cn.alphahub.mall.product.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Oss feign远程RPC文件上传
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/18
 */
@FeignClient(name = AppConstant.THIRD_PARTY_SERVICE, contextId = "ossClient")
@RequestMapping("/oss/rpc")
public interface OssClient {
    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间名称
     * @return 创建成功，返回存储空间名称
     */
    @PostMapping("/bucket/create")
    Result<String> createBucket(@RequestParam(name = "bucketName") String bucketName);

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间名称
     * @return void
     */
    @DeleteMapping("/bucket/delete")
    Result<Void> deleteBucket(@RequestParam(name = "bucketName") String bucketName);

    /**
     * 上传文件至OSS
     * <p>
     * 1. 转存网络文件
     * 2. 上传本地文件到时需要指定文件完整路径
     * </p>
     *
     * @param objectName   对象名，可以是本地文件，可以是网络文件
     * @param fileDirOfOss 文件在oss bucket中的文件夹
     * @return 文件的url
     */
    @PostMapping("/upload")
    Result<String> upload(@RequestParam(name = "objectName") String objectName, @RequestParam(name = "fileDirOfOss") String fileDirOfOss);

    /**
     * 上传文件至OSS（普通上传）
     *
     * @param file     multipart file
     * @param filename 文件名(包含后缀)
     * @return 文件完整url
     */
    @PostMapping("/upload/multipart/file")
    Result<String> upload(@RequestPart("file") MultipartFile file, @RequestParam("filename") String filename);

    /**
     * 删除单个文件
     *
     * @param objectName 文件URL
     */
    @DeleteMapping("/delete/single")
    Result<Void> deleteSingle(@RequestParam(name = "objectName") String objectName);

    /**
     * 批量删除文件
     *
     * @param objectNames 文件URL集合
     * @return 删除结果:详细模式下为删除成功的文件列表，简单模式下为删除失败的文件列表。
     */
    @DeleteMapping("/delete/many")
    Result<List<String>> deleteMany(@RequestBody List<String> objectNames);

    /**
     * 判断文件是否存在
     *
     * @param objectUrl 文件的完整url
     * @return 是否存在
     */
    @GetMapping("/file/exist/{objectUrl}")
    Result<Boolean> isFileExist(@PathVariable(name = "objectUrl") String objectUrl);

    /**
     * 判断OSS的全局命名空间(存储空间)是否存在
     *
     * @return 存在返回true，否则返回false
     */
    @GetMapping("/bucket/exist/{bucketName}")
    Result<Boolean> isBucketExist(@PathVariable(name = "bucketName") String bucketName);
}
