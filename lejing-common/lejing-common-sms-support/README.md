# 多短信模板&多短信供应商支持模块

> `lejing-common-sms-support`模块要解决的事：
>
> 如何使用一个注解`@SMS`和一个模板类`SmsTemplate`在多个**短信供应商**和**多个短信模板**之间**优雅**的切换

## 1 故事背景

我们先来看一张笔者一朋友张三（化名）所在的公司的短信模板图：

![image-20210929173756644](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210929173756644.png)

张三的苦恼是每新开一个业务模块他都要把之前发送短信的业务代码**CV**一遍，有没有优雅的一种方式呢？接下来，我们一同交流下这种问题。

通常规模稍大点的公司的业务板块比较多，对应的则是每一种业务的短信消息通知，通常情况下，短信通知分为三类:

1. 验证码通知
2. 营销通知
3. 内容通知

从以上图片中张三所在的公司短信消息的模板已达到`9`个，传统的`XxxUtils`的`CV`编码方式会大大增加代码的重复率，基于`SpringBoot`极高的可拓展性，我们可以有更优雅的编码方式。

## 2 `controller`使用效果

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
- 支持的短信提供商有`5`家：阿里云、腾讯云、华为云、京东云、七牛云

## 3 细节分享

### 3.1 配置文件

> 提示：同一短信供应商下面的多个模板的模板名称（`template-name`）不能重复。

以下是**阿里云**、**腾讯云**、**华为云**、**京东云**、**七牛云**等短信模板的配置示例，包含模板情景：同一短信供应商多个短信模板、多个短信供应商多个模板

```yaml
spring:
  sms:
    #默认线程池配置，一下参数是默认值，可根据自己的业务调整大小
    thread:
      core-pool-size: 50
      maximum-pool-size: 200
      keep-alive-time: 10
      time-unit: seconds
      capacity: 2000
    #默认短信模板配置，短信模板名称, 默认: DEFAULT, 无需修改
    template-name: DEFAULT
    #默认短信供应商，大小写不敏感，可选值: ali, huawei, jingdong, qiniu, tencent
    sms-supplier: ALI
    #短信配置
    sms-properties:
      #短信access-key
      access-key: accessKey1
      #短信secret-key
      secret-key: secretKey1
      #区域
      region-id: regionId1
      #短信签名
      sign-name: signName1
      #短信模板code、短信模板id
      template-code: templateCode1
      #短信sdk的appId，有就填，没有留空，如：腾讯云需要、阿里云不需要
      app-id: null
    #多供应商、多短信模块配置
    multi-sms-templates:
      template-properties:
        - template-name: "促销短信模板"
          sms-supplier: ALI
          sms-properties:
            access-key: accessKey1
            secret-key: secretKey1
            region-id: regionId1
            sign-name: signName1
            template-code: templateCode1
        - template-name: "秒杀短信模板"
          sms-supplier: ALI
          sms-properties:
            access-key: accessKey1
            secret-key: secretKey1
            region-id: regionId1
            sign-name: signName1
            template-code: templateCode1
        - template-name: "验证码短信模板"
          sms-supplier: HUAWEI
          sms-properties:
            access-key: accessKey2
            secret-key: secretKey2
            region-id: regionId2
            sign-name: signName2
            template-code: templateCode2
            app-id: your-app-id
        - template-name: "京东云短信验证码模板"
          sms-supplier: JINGDONG
          sms-properties:
            access-key: your-ak
            secret-key: your-sk
            region-id: cn-north-1
            sign-name: your-sign-name
            template-code: your-template-id
        - template-name: "验证码短信模板"
          sms-supplier: QINIU
          sms-properties:
            access-key: accessKey4
            secret-key: secretKey4
            region-id: regionId4
            sign-name: signName4
            template-code: templateCode4
        - template-name: "腾讯云内容短信模板"
          sms-supplier: TENCENT
          sms-properties:
            access-key: accessKey4
            secret-key: secretKey4
            region-id: "ap-nanjing"
            sign-name: "xxx的个人主页"
            template-code: 1141766
            app-id: "your-sms-sdk-app-id"
```

### 3.2 注解说明

#### 3.2.1 业务注解`@SMS`

```java
import cn.alphahub.mall.sms.SmsClient;
import cn.alphahub.mall.sms.enums.SmsSupplier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static cn.alphahub.mall.sms.SmsClient.DefaultSmsClientPlaceholder;

/**
 * 多模板短信注解
 *
 * @author lwj
 * @version 1.0
 * @apiNote 基于此注解解析不同的短信模板, 使用注解{@code @SMS}指定以：短信供应商、短信模板发送短信
 * @date 2021-09-24
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SMS {

    /**
     * 默认模板名称
     */
    String DEFAULT_TEMPLATE = "DEFAULT";

    /**
     * 短信模板名称，必须和{@code yml}配置文件里面的{@code template-name}一致，默认：DEFAULT
     *
     * @return 短信模板名称
     */
    String name() default DEFAULT_TEMPLATE;

    /**
     * 短信供应商，默认短信供应商: 阿里云
     *
     * @apiNote 如果需要拓展其他短信供应商，见枚举{@code SmsSupplier}
     * @see SmsSupplier
     */
    SmsSupplier supplier() default SmsSupplier.ALI;

    /**
     * 自定义实现发送发送短信的实现类，必须显现或继承{@code SmsClient}接口
     *
     * @return 发送短信的实现类class
     * @apiNote 当指定自定义短信发送类时将优先采用自定义短信发送实现完成发送短信的逻辑
     */
    Class<? extends SmsClient> invokeClass() default DefaultSmsClientPlaceholder.class;
}
```

`@SMS`可作用于**类**、**方法**上, 支持**自定义发送短信逻辑**实现，通过`invokeClass`指定自定义实现类。

#### 3.2.2  自动配置注解`@EnableSmsSupport`

```java
import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.aspect.SmsAspect;
import cn.alphahub.mall.sms.config.SmsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable SMS Support
 *
 * @author lwj
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SmsConfig.class, SmsAspect.class, SmsTemplate.class})
public @interface EnableSmsSupport {

}
```

注解`@EnableSmsSupport`作用于**类**上，用于需要发送短信的**web应用**启用短信支持，并自动装配配置文件，只需要在发送短信的服务的`yml`配置文件中配置`3.1`的短信`AK`、`SK`等数据即可，

### 3.3 短信提供商

目前只整合一下**5**种短信供应商的短信实现，读者可以自行增加其他短信产商的发送短信的实现

```java
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 短信供应商类型枚举枚举
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Getter
@AllArgsConstructor
public enum SmsSupplier {
    /**
     * 阿里云
     */
    ALI("ALI_CLOUD", "阿里云"),
    /**
     * 华为云
     */
    HUAWEI("HUAWEI_CLOUD", "华为云"),
    /**
     * 京东云
     */
    JINGDONG("JINGDONG_CLOUD", "京东云"),
    /**
     * 七牛云
     */
    QINIU("QINIU_CLOUD", "七牛云"),
    /**
     * 腾讯云
     */
    TENCENT("TENCENT_CLOUD", "腾讯云"),
    ;

    /**
     * 供应商名称编码
     */
    private final String code;
    /**
     * 供应商名称
     */
    private final String name;

    /**
     * 获取短信供应商枚举
     *
     * @param code 供应商名称编码
     * @return 短信供应商枚举
     */
    public static SmsSupplier getEnum(String code) {
        return Stream.of(SmsSupplier.values())
                .filter(smsSupplier -> smsSupplier.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
```

### 3.4 自定义短信发送实现

自定义短信发送实现需实现`cn.alphahub.mall.sms.SmsClient`接口并覆写`send`方法。

![image-20211008174003086](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20211008174003086.png)

### 3.5 自动装配使用说明

**步骤：**

1. 新建一个`springboot`的`web`项目, `pom.xml`中引入`lejing-common-sms-support`的`maven`坐标：

```xml
<!-- lejing-common-sms-support -->
<dependency>
    <groupId>cn.alphahub.mall.common</groupId>
    <artifactId>lejing-common-sms-support</artifactId>
     <version>1.2.0-SNAPSHOT</version>
</dependency>
```

2. 在该服务的启动类上标注注解`@EnableSmsSupport`启用短信支持

![](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20211008174758732.png)

3. 在`Service`或`Controller`层注入`SmsTemplate`，并在业务方法或业务类上标注注解`@SMS`发送短信，详见源码`cn.alphahub.mall.sms.demo.SmsServiceDemoController`

> 推荐注解`@SMS`作用于方法，方法级别使用。

![image-20211008175303335](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20211008175303335.png)

## 4 关于注解`@SMS`作用在类和方法的优先级问题

- 当注解`@SMS`同时作用类，和方法上时，方法上注解`@SMS`的优先级高于类上`@SMS`注解的优先级
- 当注解`@SMS`作用方法上时，该方法短信客户端的为注解`@SMS`指定的短信客户端
- 当注解`@SMS`作用类上时，该类所有短信模板方法发送短信的客户端都以注解`@SMS`指定为准客户端

## 5 关于`Spring IOC`容器中的同一个`Bean`实例里面被`@SMS`注解标注的方法间嵌套调用的问题

### 5.1 我们先看一个`AOP`嵌套调用的示例类：`SMSAnnotateWithClassAndMethod`

```java
import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.annotation.SMS;
import cn.alphahub.mall.sms.demo.MyCustomSmsClientDemoImpl;
import cn.alphahub.mall.sms.enums.SmsSupplier;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

import static cn.alphahub.mall.sms.SmsTemplate.SmsParam;

/**
 * 测试{@code @SMS}同时标注在方法类、类方法上面
 *
 * @author lwj
 * @version 1.0
 * @date 2021-10-02 18:18
 */
@SMS
@Service
public class SMSAnnotateWithClassAndMethod {

    @Resource
    private SmsTemplate smsTemplate;

    /**
     * 使用腾讯云发送短信
     *
     * @param smsParam 短信参数
     * @return 发送结果
     */
    @SMS(name = "腾讯云内容短信模板", supplier = SmsSupplier.TENCENT)
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
    public Object sendWithCustomSmsClient(@RequestBody SmsParam smsParam) {
        return smsTemplate.send(smsParam);
    }

    /**
     * 嵌套调用，使用Spring AOP代理当前对象
     *
     * @param smsParam 短信参数
     */
    public void nested(@RequestBody SmsParam smsParam) {
        ((SMSAnnotateWithClassAndMethod) AopContext.currentProxy()).sendWithTencentCloud(smsParam);
        ((SMSAnnotateWithClassAndMethod) AopContext.currentProxy()).sendWithCustomSmsClient(smsParam);
    }
}
```

> **以上多模板短信实例代码的应用场景：**
>
> 当用户在乐璟商城下单成功后，平台方应当发送消息通知给**用户**和**商家**，告知用户物流情况，告知商家本单交易情况。
>
> 假设`sendWithTencentCloud()`是告诉用户的短信模板消息，`sendWithCustomSmsClient()`是告诉商家的短信模板消息，为了完成以上业务场景，我们写了`nested()`方法来完成这个业务场景。

### 5.2 结论分享

- `nested()`方法一共调用两个本类里面被注解`@SMS`标注的方法完成我们的业务需求：“当用户在乐璟商城下单成功后，平台方应当发送消息通知给**用户**和**商家**，告知用户物流情况，告知商家本单交易情况”，
- 基于`CGLib`的动态代理调用本类方法完成业务时，使用的是增强代理类去执行业务方法，并不是本类`this`自身，因此我们需要从线程变量中获取当前`AOP`的真实代理对象，让真实代理对象调用本类（`this`）的方法执行业务，执行前后`AOP`能根据我们设定好的代理规则解析正确的业务参数完成业务需求。
