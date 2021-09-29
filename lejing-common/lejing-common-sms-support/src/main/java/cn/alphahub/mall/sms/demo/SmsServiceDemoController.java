package cn.alphahub.mall.sms.demo;

import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.enums.SmsSupplier;
import cn.alphahub.mall.sms.impl.MyCustomSmsClientDemoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.alphahub.mall.sms.SmsTemplate.SmsParam;

/**
 * SMS Service Test Controller
 * <p>多短信供应商、多短信模板示例Controller</p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-29 14:14
 */
@Slf4j
@RestController
@RequestMapping("/sms/support/demo")
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class SmsServiceDemoController {

    @Resource
    private SmsTemplate smsTemplate;

    /**
     * 使用默认短信模板发送短信
     * <p>默认模板可以注解{@code @SMS}，也可以不加</p>
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @PostMapping("/sendWithDefaultTemplate")
    public Object sendWithDefaultTemplate(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用阿里云短信模板1发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "促销短信模板")
    @PostMapping("/sendWithAliCloud1")
    public Object sendWithAliCloud1(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用阿里云短信模板2发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "秒杀短信模板")
    @PostMapping("/sendWithAliCloud2")
    public Object sendWithAliCloud2(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用华为云发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "验证码短信模板", supplier = SmsSupplier.HUAWEI)
    @PostMapping("/sendWithHuaweiCloud")
    public Object sendWithHuaweiCloud(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用京东云发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "京东云短信验证码模板", supplier = SmsSupplier.JINGDONG)
    @PostMapping("/sendWithJingdongCloud")
    public Object sendWithJingdongCloud(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用七牛云发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "验证码短信模板", supplier = SmsSupplier.QINIU)
    @PostMapping("/sendWithQiniuCloud")
    public Object sendWithQiniuCloud(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 使用腾讯云发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "内容短信模板", supplier = SmsSupplier.TENCENT)
    @PostMapping("/sendWithTencentCloud")
    public Object sendWithTencentCloud(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 自定义短信实现发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(invokeClass = MyCustomSmsClientDemoImpl.class)
    @PostMapping("/sendCustomSmsClient")
    public Object sendWithCustomSmsClient(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }
}
