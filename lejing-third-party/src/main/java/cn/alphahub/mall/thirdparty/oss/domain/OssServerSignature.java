package cn.alphahub.mall.thirdparty.oss.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * OSS服务端签名
 * <b>服务端签名后直传</b>
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssServerSignature implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 你的accessKeyId
     */
    private String accessId;

    /**
     * 策略
     */
    private String policy;

    /**
     * 签名
     */
    private String signature;

    /**
     * user-dir-prefix/
     */
    private String dir;

    /**
     * "https://" + bucket + "." + endpoint
     */
    private String host;

    /**
     * 过期时间
     */
    private String expire;

    /**
     * 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。
     * OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
     * 本例中修改为：String callbackUrl ="http://11.22.33.44:1234"
     */
    private String callbackUrl;

}
