package cn.alphahub.mall.thirdparty.oss.controller.rpc;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.thirdparty.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Oss远程RPC文件上传Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/17
 */
@Slf4j
@RestController
@RequestMapping("/oss/rpc")
public class OssRpcController {
    @Resource
    private OssService ossService;

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间名称
     * @return 创建成功，返回存储空间名称
     */
    @PostMapping("/bucket/create")
    public Result<String> createBucket(@RequestParam(name = "bucketName") String bucketName) {
        String bucket = ossService.createBucket(bucketName);
        return Result.of(bucket);
    }

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间名称
     */
    @DeleteMapping("/bucket/delete")
    public Result<Void> deleteBucket(@RequestParam(name = "bucketName") String bucketName) {
        ossService.deleteBucket(bucketName);
        return Result.ok();
    }

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
    public Result<String> upload(@RequestParam(name = "objectName") String objectName, @RequestParam(name = "fileDirOfOss") String fileDirOfOss) {
        String upload = ossService.upload(objectName, fileDirOfOss);
        return Result.of(upload);
    }

    /**
     * 上传文件至OSS（普通上传）
     *
     * @param file     multipart file
     * @param filename 文件名(包含后缀)
     * @return 文件完整url
     */
    @PostMapping("/upload/multipart/file")
    public Result<String> upload(@RequestPart("file") MultipartFile file, @RequestParam("filename") String filename) throws IOException {
        String upload = ossService.upload(file, filename);
        return Result.of(upload);
    }

    /**
     * 删除单个文件
     *
     * @param objectName 文件URL
     */
    @DeleteMapping("/delete/single")
    public Result<Void> deleteSingle(@RequestParam(name = "objectName") String objectName) {
        ossService.deleteOne(objectName);
        return Result.ok();
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件URL集合
     * @return 删除结果:详细模式下为删除成功的文件列表，简单模式下为删除失败的文件列表。
     */
    @DeleteMapping("/delete/many")
    public Result<List<String>> deleteMany(@RequestBody List<String> objectNames) {
        List<String> many = ossService.deleteMany(objectNames);
        return Result.of(many);
    }

    /**
     * 判断文件是否存在
     *
     * @param objectUrl 文件的完整url
     * @return 是否存在
     */
    @GetMapping("/file/exist/{objectUrl}")
    public Result<Boolean> isFileExist(@PathVariable(name = "objectUrl") String objectUrl) {
        boolean fileExist = ossService.isFileExist(objectUrl);
        return Result.of(fileExist);
    }

    /**
     * 判断OSS的全局命名空间(存储空间)是否存在
     *
     * @return 存在返回true，否则返回false
     */
    @GetMapping("/bucket/exist/{bucketName}")
    public Result<Boolean> isBucketExist(@PathVariable(name = "bucketName") String bucketName) {
        boolean bucketExist = ossService.isBucketExist(bucketName);
        return Result.of(bucketExist);
    }
}
