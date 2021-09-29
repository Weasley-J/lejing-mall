package cn.alphahub.mall.sms.impl;

import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.exception.SmsParamEmptyException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.alphahub.mall.sms.config.SmsConfig.SmsProperties;

/**
 * 华为云短信实现
 *
 * @author lwj
 * @version 1.0
 * @apiNote <a href='https://support.huaweicloud.com/devg-msgsms/sms_04_0002.html'>华为云短信帮助链接</a>，暂不支持个人用户申请
 * @date 2021-09-24
 */
@Slf4j
@Component
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class DefaultHuaweiCloudSmsClientImpl implements SmsClient {
    /**
     * 无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
     */
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    /**
     * 无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
     */
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    /**
     * 国内短信签名通道号或国际/港澳台短信通道号（跟据自己的配置修改）
     */
    private static final String SENDER = "csms12345678";
    /**
     * 短信配置元数据
     */
    private final SmsProperties smsProperties;

    public DefaultHuaweiCloudSmsClientImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public Object send(String content, String... phones) {
        log.info("content:{}, phones:{}", content, JSONUtil.toJsonStr(phones));
        if (paramsIsEmpty(content, phones)) {
            throw new SmsParamEmptyException("sms content or phones is empty.");
        }
        //必填,请参考"开发准备"获取如下数据,替换为实际值
        //APP接入地址+接口访问URI
        String url = "https://rtcsms.cn-north-1.myhuaweicloud.com:10743/sms/batchSendSms/v1";
        //APP_Key
        String appKey = this.smsProperties.getAppId();
        //APP_Secret
        String appSecret = this.smsProperties.getSecretKey();
        //模板ID
        String templateId = this.smsProperties.getTemplateCode();

        //条件必填,国内短信关注,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
        //国际/港澳台短信不用关注该参数
        //签名名称
        String signature = this.smsProperties.getSignName();

        //必填,全局号码格式(包含国家码),示例:+8615123456789,多个号码之间用英文逗号分隔
        //短信接收人号码
        String receiver = StringUtils.join(phones, ",");

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

        /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为"您的验证码是${1}"时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${1}件快递请到${2}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         * 模板中的每个变量都必须赋值，且取值不能为空
         * 查看更多模板和变量规范:产品介绍>模板和变量规范
         */
        //模板变量，此处以单变量验证码短信为例，请客户自行生成6位验证码，并定义为字符串类型，以杜绝首位0丢失的问题（例如：002569变成了2569）。
        String templateParas = "[" + content + "]";

        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(SENDER, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            log.error("body is null.");
            return null;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            log.error("wsse header is null.");
            return null;
        }

        Writer out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        HttpsURLConnection connection;
        InputStream is = null;

        HostnameVerifier hv = (hostname, session) -> true;

        try {
            trustAllHttpsCertificates();
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }

        try {
            URL realUrl = new URL(url);
            connection = (HttpsURLConnection) realUrl.openConnection();

            connection.setHostnameVerifier(hv);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(true);
            //请求方法
            connection.setRequestMethod("POST");
            //请求Headers参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            connection.setRequestProperty("X-WSSE", wsseHeader);

            connection.connect();
            out = new OutputStreamWriter(connection.getOutputStream());
            //发送请求Body参数
            out.write(body);
            out.flush();
            out.close();

            int status = connection.getResponseCode();
            //200
            if (200 == status) {
                is = connection.getInputStream();
            } else { //400/401
                is = connection.getErrorStream();
            }
            in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            //打印响应消息实体
            log.info("result:{}", JSONUtil.toJsonStr(result.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }


    /**
     * 构造请求Body体
     *
     * @param sender         sender
     * @param receiver       receiver
     * @param templateId     templateId
     * @param templateParas  templateParas
     * @param statusCallBack statusCallBack
     * @param signature      | 签名名称,使用国内短信通用模板时填写
     * @return RequestBody
     */
    private String buildRequestBody(String sender, String receiver, String templateId, String templateParas, String statusCallBack, String signature) {
        if (null == sender || null == receiver || null == templateId
                || sender.isEmpty() || receiver.isEmpty() || templateId.isEmpty()) {
            log.error("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        Map<String, String> map = new HashMap<>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            temp = URLEncoder.encode(value, StandardCharsets.UTF_8);
            sb.append(key).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 构造X-WSSE参数值
     *
     * @param appKey    appKey
     * @param appSecret appSecret
     * @return X-WSSE参数值
     */
    private String buildWsseHeader(String appKey, String appSecret) {
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(appSecret)) {
            log.warn("{}", "buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        //Created
        String time = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
        //Nonce
        String nonce = IdUtil.fastSimpleUUID();

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        //PasswordDigest
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest);
        //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
        // PasswordDigest
        //String passwordDigestBase64Str = Base64.encodeBase64String(passwordDigest);
        //若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
        //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    /**
     * trust all https certificates
     *
     * @throws Exception
     */
    private void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

}
