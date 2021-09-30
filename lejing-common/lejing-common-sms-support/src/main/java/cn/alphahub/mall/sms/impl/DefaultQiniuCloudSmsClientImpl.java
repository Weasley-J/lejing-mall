package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;

/**
 * 七牛云短信实现
 *
 * @author lwj
 * @version 1.0
 * @apiNote <a href='https://developer.qiniu.com/sms/5897/sms-api-send-message'>七牛云短信帮助书文档</a>
 * @date 2021-09-24
 */
@Slf4j
@Component
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class DefaultQiniuCloudSmsClientImpl implements SmsClient {

    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultQiniuCloudSmsClientImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        // 设置需要操作的账号的AK和SK
        Auth auth = Auth.create(smsProperties.getAccessKey(), smsProperties.getSecretKey());
        // 实例化一个SmsManager对象
        SmsManager smsManager = new SmsManager(auth);
        Response resp = null;
        try {
            // 短信模板code、短信模板id为空发送全文短信
            if (StringUtils.isBlank(smsProperties.getTemplateCode())) {
                resp = smsManager.sendFulltextMessage(phones, content);
            } else {
                // 自定义模板变量（更具自己的情况修改），变量设置在创建模板时，参数template指定, 传递的变量参数不支持中文，请使用英语或数字
                Map<String, String> map = new HashMap<>(5);
                map.put("template", content);
                resp = smsManager.sendMessage(smsProperties.getTemplateCode(), phones, map);
            }

            /*
            resp = smsManager.describeSignature("passed", 0, 0);
            resp = smsManager.createSignature("signature", "app", new String[]{"data:image/gif;base64,xxxxxxxxxx"});
            resp = smsManager.describeTemplate("passed", 0, 0);
            resp = smsManager.createTemplate("name", "template", "notification", "test", "signatureId");
            resp = smsManager.modifyTemplate("templateId", "name", "template", "test", "signatureId");
            resp = smsManager.modifySignature("SignatureId", "signature");
            resp = smsManager.deleteSignature("signatureId");
            resp = smsManager.deleteTemplate("templateId");
            */

            log.info("response:{}", JSONUtil.toJsonStr(resp));

            /*
            SignatureInfo sinfo = smsManager.describeSignatureItems("", 0, 0);
            System.out.println(sinfo.getItems().get(0).getAuditStatus());
            TemplateInfo tinfo = smsManager.describeTemplateItems("", 0, 0);
            System.out.println(tinfo.getItems().get(0).getAuditStatus());
            */

        } catch (QiniuException e) {
            log.error("{}", e.getLocalizedMessage(), e);
            Map<String, Object> responseMap = new LinkedHashMap<>(1);
            responseMap.put("error", e.getLocalizedMessage());
            return JSONUtil.toJsonStr(responseMap);
        }
        return resp;
    }
}
