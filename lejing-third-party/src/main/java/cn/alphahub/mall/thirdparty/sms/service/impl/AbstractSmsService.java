package cn.alphahub.mall.thirdparty.sms.service.impl;

import cn.alphahub.mall.thirdparty.sms.service.SmsService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pbting
 */
public abstract class AbstractSmsService implements SmsService {

    private final ConcurrentHashMap<String, IAcsClient> acsClientConcurrentHashMap = new ConcurrentHashMap<>(6);

    @Override
    public IAcsClient getHangZhouRegionClientProfile(String accessKeyId, String accessKeySecret) {
        return acsClientConcurrentHashMap.computeIfAbsent(getKey("cn-hangzhou", accessKeyId, accessKeySecret),
                (acsClient) -> new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret))
        );
    }

    private String getKey(String regionId, String accessKeyId, String accessKeySecret) {
        return regionId + ":" + accessKeyId + ":" + accessKeySecret;
    }

}
