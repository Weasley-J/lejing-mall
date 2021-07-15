package cn.alphahub.mall.thirdparty.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


/**
 * 阿里云OSS属性
 *
 * @author liuwenjing
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alibaba.cloud")
public class OssProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * access-key
     */
    private String accessKey;
    /**
     * secret-key
     */
    private String secretKey;
    /**
     * OSS存储空间名称
     * <p>存储空间是OSS的全局命名空间，相当于数据的容器，可以存储若干文件</p>
     */
    private String bucketName;
    /**
     * Bucket域名前缀
     * <p>
     * 访问域名带上文件路径，即可通过互联网访问到 Bucket 内的任意文件
     * 如果 ACL 是私有读，则还需要带上签名，
     * URL 拼写规则：{@code https://${alibaba.cloud.bucket-name}.${alibaba.cloud.oss.endpoint}}, 如：<br/>
     * https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/object
     * </p>
     */
    private String hostPrefix;
    /**
     * 外网可访问的url前缀：https://${alibaba.cloud.bucket-name}.${alibaba.cloud.oss.endpoint}/
     * <p>直接拼后缀外网可访问</p>
     */
    private String urlPrefix;
    /**
     * 存储空间所在的地域对应的访问域名
     * <p>
     * <b>存储空间所在的地域对应的访问域名</b>
     *     <ul>
     *         <li>OSS的访问地址为固定格式：BucketName.Endpoint</li>
     *         <a href="https://help.aliyun.com/knowledge_detail/39584.html?spm=a2c4g.11186623.6.621.71e2414f7WKEBH">ECS实例通过OSS内网地址访问OSS资源</a>
     *         <a href="https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.22.606a7f5egqBfwS#concept-zt4-cvy-5db">各地域Endpoint详情请参见</a>
     *        <li>
     *            通过OSS内网地址访问OSS资源有以下两种方式：
     * <p>与OSS同地域ECS实例可以直接通过内网访问有权限的OSS资源</p>
     * <p>与OSS不同地域的ECS实例或公网用户可通过配置ECS反向代理，间接实现通过OSS内网地址访问OSS资源</p>
     *         </li>
     *     </ul>
     * </p>
     */
    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;

}
