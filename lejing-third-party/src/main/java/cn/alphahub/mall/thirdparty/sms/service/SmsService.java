package cn.alphahub.mall.thirdparty.sms.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 短信顶层接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
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

    /**
     * 查询短信发送详情
     *
     * @param telephone 手机号
     * @param sendDate  发送日期
     * @return 发送结果
     */
    QuerySendDetailsResponse querySendDetails(String telephone, @DateTimeFormat(pattern = "yyyy-MM-dd") Date sendDate);

    /**
     * <b>发送验证码</b>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 是否成功
     */
    Boolean sendCheckCode(String phone, Integer origin);
}
