package cn.alphahub.mall.thirdparty.sms.service.impl;

import cn.alphahub.mall.thirdparty.config.SmsProperties;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pbting
 */
@Slf4j
@Service
public final class SmsServiceImpl extends AbstractSmsService {

    @Resource
    private SmsProperties smsProperties;

    @Override
    public QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request, String accessKeyId, String accessKeySecret) throws ClientException {
        return getHangZhouRegionClientProfile(accessKeyId, accessKeySecret).getAcsResponse(request);
    }

    @Override
    public QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request) throws ClientException {
        return querySendDetails(request, smsProperties.getAccessKeyId(), smsProperties.getAccessSecret());
    }
}
