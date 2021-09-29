package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.json.JSONUtil;
import com.jdcloud.sdk.auth.CredentialsProvider;
import com.jdcloud.sdk.auth.StaticCredentialsProvider;
import com.jdcloud.sdk.http.HttpRequestConfig;
import com.jdcloud.sdk.http.Protocol;
import com.jdcloud.sdk.service.sms.model.BatchSendRequest;
import com.jdcloud.sdk.service.sms.model.BatchSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;

/**
 * 京东云短信实现
 *
 * @author lwj
 * @version 1.0
 * @implNote 京东云暂不支持申请个人账号短信
 * @date 2021-09-24
 */
@Slf4j
@Component
public class DefaultJingdongCloudSmsClientImpl implements SmsClient {
    /**
     * 地域信息不用修改
     */
    private static final String REGION = "cn-north-1";
    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultJingdongCloudSmsClientImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        BatchSendRequest request = new BatchSendRequest();
        request.setRegionId(ObjectUtils.defaultIfNull(smsProperties.getRegionId(), REGION));
        // 设置模板ID 应用管理-文本短信-短信模板 页面可以查看模板ID
        request.setTemplateId(smsProperties.getTemplateCode());
        // 设置签名ID 应用管理-文本短信-短信签名 页面可以查看签名ID
        request.setSignId(smsProperties.getSignName());
        // 设置下发手机号list
        request.setPhoneList(Arrays.asList(phones));
        // 设置模板参数，非必传，如果模板中包含变量请填写对应参数，否则变量信息将不做替换。
        request.setParams(Arrays.asList(StringUtils.split(content, ",")));
        BatchSendResponse response = this.getSmsClient().batchSend(request);
        log.info("response:{}", JSONUtil.toJsonStr(response));
        return response;
    }

    /**
     * 初始换京东云短信客户端
     *
     * @return {@code com.jdcloud.sdk.service.sms.client.SmsClient}
     * @apiNote 普通用户ak/sk （应用管理-文本短信-概览 页面可以查看自己ak/sk）
     */
    public com.jdcloud.sdk.service.sms.client.SmsClient getSmsClient() {
        // 请填写AccessKey ID
        String accessKeyId = smsProperties.getAccessKey();
        // 请填写AccessKey Secret
        String secretAccessKey = smsProperties.getSecretKey();
        CredentialsProvider credentialsProvider = new StaticCredentialsProvider(accessKeyId, secretAccessKey);
        return com.jdcloud.sdk.service.sms.client.SmsClient.builder()
                .credentialsProvider(credentialsProvider)
                //默认为HTTPS
                .httpRequestConfig(new HttpRequestConfig.Builder().protocol(Protocol.HTTP).build())
                .build();
    }
}
