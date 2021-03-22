package cn.alphahub.mall.thirdparty.sms.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;

/**
 * 短信顶层接口
 *
 * @author liuwenjing
 */
public interface SmsService {
    /**
     * 获取杭州区域客户端配置
     *
     * @param accessKeyId accessKey
     * @param secret      secret
     * @return
     */
    IAcsClient getHangZhouRegionClientProfile(String accessKeyId, String secret);

    /**
     * 查询发送详情
     *
     * @param request         QuerySendDetailsRequest对象
     * @param accessKeyId     accessKey
     * @param accessKeySecret accessKeySecret
     * @return
     * @throws ClientException 客户端异常
     */
    QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request, String accessKeyId, String accessKeySecret) throws ClientException;

    /**
     * 查询发送详情
     *
     * @param request QuerySendDetailsRequest
     * @return QuerySendDetailsResponse
     * @throws ClientException 客户端异常
     */
    QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request) throws ClientException;
}
