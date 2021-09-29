# 多短信模板&多短信供应商支持模块



> `lejing-common-sms-support`模块要解决的事：
>
> 如何使用一个注解`@SMS`和一个模板类`SmsTemplate`在多个**短信供应商**和**多个短信模板**之间**优雅**的切换



## 1 故事背景



我们先来看一张笔者一朋友所在的公司的短信模板图：

![image-20210929173756644](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210929173756644.png)



他的苦恼是每新开一个业务模块他都要把之前发送短信的业务代码**CV**一遍，有没有优雅的一种方式呢？接下来，我们一同交流下这种问题。

通常规模稍大点的公司的业务板块比较多，对应的则是每一种业务的短信消息通知，通常情况下，短信通知分为三类:

1. 验证码通知
2. 营销通知
3. 内容通知



从以上图片中笔者朋友所在的公司短信消息的模板已达到`9`个，传统的`XxxUtils`的`CV`编码方式会大大增加代码的重复率，基于`SpringBoot`极高的可拓展性，我们可以有更优雅的编码方式。



## 2 废话少说看`controller`使用效果

```java
import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.enums.SmsSupplier;
import cn.alphahub.mall.sms.demo.MyCustomSmsClientDemoImpl;
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

```



说明：

- 上面上`8`个发送短信消息的方法都是调用`SmsTemplate`类的`send(SmsParam smsParam)`方法
- 通过注解` @SMS`动态切换短信模板
- 第`8`个方法支持自定义短信实现，通过注解`@SMS(invokeClass = MyCustomSmsClientDemoImpl.class)`指定自定义短信实现类
- 通过注解`@SMS`在**同一短信提供商的多个短信模板**、**不同短信提供上商多个短信模板**之间自由切换短信模板
- 发送短信的方法只有一个`send()`方法
- 支持的短信提供上有`5`家：阿里云、腾讯云、华为云、京东云、七牛云



## 3 细节分享

### 3.1 配置文件

`todo`



### 3.2 注解说明

`todo`



### 3.3 短信提供商

`todo`



### 3.4 自定义短信发送实现

`todo`



### 3.5 自动装配使用说明

`todo`

