package cn.alphahub.mall.sms.controller;

import cn.alphahub.mall.sms.config.SmsProperties;
import cn.alphahub.mall.sms.listener.SmsReportMessageListener;
import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyun.mns.model.Message;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信Controller
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@RestController
public class SmsController {

    @Resource
    private Environment environment;
    @Resource
    private ISmsService smsService;
    @Resource
    private SmsReportMessageListener smsReportMessageListener;
    @Resource
    private SmsProperties smsProperties;

    /**
     * 发送单个验证码
     *
     * @param telephone 手机号
     * @param code      验证码
     * @return send sms response
     */
    @PostMapping("/sms-send.do")
    public SendSmsResponse sendCheckCode(@RequestParam(name = "telephone") String telephone,
                                         @RequestParam(name = "code") String code
    ) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(telephone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(smsProperties.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsProperties.getTemplateCode());
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("****TraceId");
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendSmsResponse();
    }

    /**
     * query send details response
     *
     * @param telephone 手机号
     * @return query send details response
     */
    @GetMapping("/query.do")
    public QuerySendDetailsResponse querySendDetailsResponse(@RequestParam(name = "telephone") String telephone) {
        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        request.setSendDate("20190103");
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            QuerySendDetailsResponse response = smsService.querySendDetails(request);
            return response;
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return new QuerySendDetailsResponse();
    }

    /**
     * get sms report message set
     *
     * @return sms report message set
     */
    @GetMapping("/sms-report.do")
    public List<Message> smsReport() {
        return smsReportMessageListener.getSmsReportMessageSet();
    }

    /**
     * get sms report message set
     *
     * @return sms report queue name
     */
    @GetMapping("/report-queue.do")
    public String getSmsReportQueueName() {
        return environment.getProperty("alibaba.cloud.sms.up-queue-name");
    }
}
