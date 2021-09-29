package cn.alphahub.mall.thirdparty.sms.util;

import cn.alphahub.common.core.domain.SmsParam;
import cn.alphahub.mall.thirdparty.config.AliyunConfig;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>阿里云短信工具类</p>
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Slf4j
@Component
public class AliyunSmsUtil {
    /**
     * 短信API产品名称(短信产品名固定, 无需修改)
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 短信API产品域名 (接口地址固定, 无需修改)
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String VERSION = "2017-05-25";
    private static final String SEND_SMS = "SendSms";
    private static final String REGION_ID = "cn-hangzhou";

    @Resource
    private IAcsClient acsClient;
    @Resource
    private AliyunConfig.SmsProperties smsProperties;

    /**
     * 发送验证码给用户手机
     *
     * @param smsParam 短信参数实体
     * @return CommonResponse，可以获取响应消息
     */
    public CommonResponse sendSms(SmsParam smsParam) {
        CommonResponse response = new CommonResponse();
        if (Objects.isNull(smsParam)) {
            log.warn("短信参数不能为空!");
            return null;
        }
        String code = smsParam.getCode();
        String[] phoneNumbers = smsParam.getPhone();

        boolean b1 = Objects.isNull(code)
                || ObjectUtils.isEmpty(phoneNumbers)
                || StringUtils.isBlank(code)
                || phoneNumbers[0].isEmpty();

        if (!b1) {

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain(DOMAIN);
            request.setSysVersion(VERSION);
            request.setSysAction(SEND_SMS);

            request.putQueryParameter("RegionId", REGION_ID);
            // 支持对多个手机号码发送短信，手机号码之间以英文逗号","分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
            // 验证码类型短信，建议使用单独发送的方式。
            request.putQueryParameter("SignName", smsProperties.getSignName());
            request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phoneNumbers, ","));
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

            try {
                response = acsClient.getCommonResponse(request);
            } catch (ClientException e) {
                log.error("发送短信异常：{}", e.getMessage(), e);
            }
            log.info("发送短信状态: {}", response.getData());
        } else {
            response.setData("手机号、验证码为空！");
            log.warn("发送短信状态: {}", response.getData());
        }
        return response;
    }

    /**
     * 发送验证码给用户手机
     * <p>map入参用法:</p>
     * <pre>
     * Map<String, Object> map = new LinkedHashMap<>();
     * //一个验证码，字符类型
     * map.put("code", 785964);
     * //支持对多个手机号码发送短信,上限为1000个手机号码
     * map.put("phone", Arrays.asList("13012341234", "13812347894"));
     * //发送短信
     * sendSms(map);
     * </pre>
     *
     * @param map 入参map
     * @return CommonResponse，可以获取响应消息
     */
    @SuppressWarnings({"unchecked"})
    public CommonResponse sendSms(Map<String, Object> map) {

        CommonResponse response = new CommonResponse();

        boolean flag = CollectionUtils.isEmpty(map)
                || Objects.isNull(map.get("phone"))
                || Objects.isNull(map.get("code"))
                || StringUtils.isBlank(map.get("phone").toString())
                || StringUtils.isBlank(map.get("code").toString());

        if (flag) {
            response.setData("手机号、验证码为空！");
            log.warn("发送短信状态: {}", response.getData());
            return response;
        }

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DOMAIN);
        request.setSysVersion(VERSION);
        request.setSysAction(SEND_SMS);

        request.putQueryParameter("RegionId", REGION_ID);
        //支持对多个手机号码发送短信，手机号码之间以英文逗号","分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
        //验证码类型短信，建议使用单独发送的方式。
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + map.get("code") + "\"}");

        Object phoneObj = map.get("phone");
        if (phoneObj instanceof List) {
            List<String> phones = Collections.unmodifiableList((List<String>) phoneObj);
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phones, ","));
        } else if (phoneObj instanceof String) {
            String phone = (String) phoneObj;
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phone, ","));
        } else if (phoneObj instanceof Long) {
            Long phone = (Long) phoneObj;
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phone, ","));
        } else if (phoneObj instanceof String[]) {
            String[] phones = (String[]) phoneObj;
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phones, ","));
        } else if (phoneObj instanceof Long[]) {
            Long[] phones = (Long[]) phoneObj;
            request.putQueryParameter("PhoneNumbers", StringUtils.join(phones, ","));
        }

        try {
            response = acsClient.getCommonResponse(request);
        } catch (ClientException e) {
            log.error("发送短信异常：{}", e.getMessage(), e);
        }
        log.info("发送短信状态: {}", response.getData());

        return response;
    }

    /**
     * 发送验证码给用户手机
     *
     * @param code   验证码
     * @param phones 手机号,可以是多个
     * @return CommonResponse，可以获取响应消息
     */
    public CommonResponse sendSms(String code, String... phones) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("code", code);
        map.put("phone", Arrays.asList(phones));
        return this.sendSms(map);
    }
}
