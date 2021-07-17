package cn.alphahub.mall.thirdparty.oss.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.thirdparty.config.OssProperties;
import cn.alphahub.mall.thirdparty.oss.domain.OssServerSignature;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OSS Policy文件上传Controller
 *
 * @author liuwenjing
 */
@Slf4j
@RestController
public class OssPolicyController {

    @Resource
    private OSS ossClient;
    @Resource
    private OssProperties ossProperties;

    /**
     * 获取OSS文件上传服务端签名
     *
     * @return OSS服务端签名
     */
    @GetMapping("/oss/policy")
    public BaseResult<OssServerSignature> policy() {
        String host = ossProperties.getHostPrefix();
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        //String callbackUrl = "http://88.88.88.88:8888";
        // 用户上传文件时指定的前缀。在bucket创建一个以日期为文件夹的目录里
        String dir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Map<String, String> respMap;
        OssServerSignature ossServerSignature = new OssServerSignature();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            String expire = String.valueOf(expireEndTime / 1000);
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<>();
            respMap.put("accessid", ossProperties.getAccessKey());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", expire);

            ossServerSignature = OssServerSignature.builder()
                    .accessId(ossProperties.getAccessKey())
                    .policy(encodedPolicy)
                    .signature(postSignature)
                    .dir(dir)
                    .host(host)
                    .expire(expire)
                    .callbackUrl(null)
                    .build();

            //下面是跨域设置，在网关统一解决跨域
            /*
             JSONObject jasonCallback = new JSONObject();
             jasonCallback.put("callbackUrl", callbackUrl);
             jasonCallback.put("callbackBody",
             "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
             jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
             String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
             respMap.put("callback", base64CallbackBody);

             JSONObject ja1 = JSONObject.fromObject(respMap);
             // System.out.println(ja1.toString());
             response.setHeader("Access-Control-Allow-Origin", "*");
             response.setHeader("Access-Control-Allow-Methods", "GET, POST");
             response(request, response, ja1.toString());
             */
        } catch (Exception e) {
            log.error("异常：{}", e.getMessage(), e);
        } finally {
            ossClient.shutdown();
        }
        return BaseResult.ok(ossServerSignature);
    }


}
