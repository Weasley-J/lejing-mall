# `lejing-common-email-support`共享模块使用指南



> 此模块的所要提供的核心功能：
>
> 1. 提供多邮件模板发送邮件支持
> 2. 使用`@Email(name=“yourTemplateName”)`指定以哪一个邮箱模板发送
> 3. 使用`EmailTemplate`直接`@Resouce`注入`IOC`，然后调用此对象示例的`send`方法直接发送
> 4. 支持两种类型的邮件：（1）简单文本邮件，（2）带附件支持HTML显示的邮件



## 1 采用`application.yml`配置

### 1.1 修改配置文件: application-email.yml

```yaml
spring:
  #默认邮件模板
  mail:
    host: "smtp.189.cn"
    port: 465
    username: "xxx@189.cn"
    password: "your_password"
    protocol: "smtp"
    properties:
      mail:
        smtp:
          ssl:
            enable: true
        debug: false
    # 多邮件模板配置列表
    email-templates:
      #qq邮件模板
      - template-name: EmailQQ
        mail-properties:
          host: "smtp.qq.com"
          port: 465
          username: "xxx@qq.com"
          password: "your_password"
          protocol: "smtp"
          properties:
            mail:
              smtp:
                ssl:
                  enable: true
              debug: false
      #outlook邮件模板
      - template-name: EmailOffice365
        mail-properties:
          host: "smtp.office365.com"
          port: 587
          username: "xxx@outlook.com"
          password: "your_password"
          protocol: "smtp"
          properties:
            mail:
              smtp:
                starttls:
                  enable: true
              debug: false
      #163邮件模板
      - template-name: Email163
        mail-properties:
          host: "smtp.163.com"
          port: 587
          username: "xxx@163.com"
          password: "your_password"
          protocol: "smtp"
          properties:
            mail:
              smtp:
                ssl:
                  enable: true
              debug: false
```



### 1.2 需要发邮件服务导入`lejing-common-email-support`的maven坐标

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>lejing-mall</artifactId>
        <groupId>cn.alphahub.mall</groupId>
        <version>1.1.5-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.alphahub.mall</groupId>
    <artifactId>lejing-site-reserve</artifactId>
    <version>1.1.5-SNAPSHOT</version>
    <description>乐璟商城-场地预约服务</description>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- 邮件支持模块 -->
        <dependency>
            <groupId>cn.alphahub.mall.common</groupId>
            <artifactId>lejing-common-email-support</artifactId>
        </dependency>
          <!-- spring web启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- spring boot测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```



### 1.3 需要发邮件的服务配置加载`lejing-common-email-support` 模块邮件模板元数据

路径: `lejing-common/lejing-common-email-support/src/main/resources/application-email.yml`

**application-email.yml**里面的元数据作为**共享配置数据**的方式引入其他需要发送邮件的服务共享，通过**spring.profiles.include=email**的方式引入

需要发邮件的目标服务的**application.yml**配置如下：

```yaml
spring:
  application:
    name: lejing-site-reserve
  profiles:
    active: dev
    #加载common工程配置文件 'application-email.yml' 邮件配置元数据, spring.profiles.include: email
    include: email
```



### 1.4 使用核心注解@Email指定邮件模板

```java
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 提供不同邮件模板发送邮件的注解
 *
 * @author lwj
 * @version 1.0.0
 * @apiNote 基于此注解解析不同的邮件模板, 使用注解@Email指定以哪个模板发送邮件
 */
@Documented
@Target({TYPE, TYPE_USE, TYPE_PARAMETER, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    /**
     * 默认模板名称
     */
    String DEFAULT_TEMPLATE = "DEFAULT";

    /**
     * 邮件模板名称，默认：DEFAULT
     *
     * @return 邮件模板名称
     */
    String name() default DEFAULT_TEMPLATE;
}
```

使用方式见`1.6`节`EmailController`



### 1.5 使用EmailTemplate发送邮件

EmailTemplate提供了两个核心方法如下：

```java
    /**
     * 发送给定的简单邮件消息
     *
     * @param domain the message to send
     * @throws MailException Base class for all mail exceptions
     */
    public void send(@Valid SimpleMailMessageDomain domain) throws MailException {
        // no dump
    }

    /**
     * 发送带附件的邮件
     *
     * @param domain metadata of message to send
     * @param file   Nullable, support for spring MVC upload file received in the request, can be null.
     * @throws MailException Base class for all mail exceptions
     */
    public void send(@Valid MimeMessageDomain domain, @Nullable MultipartFile file) throws MessagingException {
         // no dump
    }
```

### 1.6 编写EmailController发送邮件

```java
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import static cn.alphahub.mall.email.EmailTemplate.MimeMessageDomain;
import static cn.alphahub.mall.email.EmailTemplate.SimpleMailMessageDomain;

/**
 * 邮件Controller
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-09 14:01
 */
@Slf4j
@RestController
@RequestMapping("/site/email")
public class EmailController {

    @Resource
    private EmailTemplate emailTemplate;

    /**
     * 发送给定的简单邮件消息
     *
     * @param message 简单邮件消息对象
     * @return ok
     * @apiNote 次方便没有标注注解@Email，则会采用默认方法邮件模板[spring.mail.xxx]发送邮件
     */
    @PostMapping("/simple/send")
    public BaseResult<Void> sendSimpleEmail(@ModelAttribute(name = "message") @Validated SimpleMailMessageDomain message) {
        log.info("send simple email:{}", message);
        emailTemplate.send(message);
        return BaseResult.ok();
    }

    /**
     * 发送带附件的邮件消息
     *
     * @param message Mime邮件消息对象
     * @param file    选择文件上传，和参数filepath二选一即可
     * @return tips
     * @apiNote 此方法标注注解@Email，则会采用注解值里面name的属性值的参数发送邮件
     */
    @Email(name = "EmailOffice365")
    @PostMapping("/mime/send")
    public BaseResult<Void> sendMimeEmail(@ModelAttribute(name = "message") @Validated MimeMessageDomain message,
                                          @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        log.info("send mime email:{}", message);
        try {
            emailTemplate.send(message, file);
        } catch (MessagingException e) {
            log.error("domain:{},{}", message, e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }
}
```



**方法说明：**

- 发送给定的简单邮件消息

```java
    /**
     * 发送给定的简单邮件消息
     *
     * @param message 简单邮件消息对象
     * @return ok
     * @apiNote 次方便没有标注注解@Email，则会采用默认方法邮件模板[spring.mail.xxx]发送邮件
     */
    @PostMapping("/simple/send")
    public BaseResult<Void> sendSimpleEmail(@ModelAttribute(name = "message") @Validated SimpleMailMessageDomain message);
```

此方法没有标注`@Email`注解指定邮件模板，则会使用默认邮件模板发送。



- 发送带附件的邮件消息

```java
    /**
     * 发送带附件的邮件消息
     *
     * @param message Mime邮件消息对象
     * @param file    选择文件上传，和参数filepath二选一即可
     * @return tips
     * @apiNote 此方法标注注解@Email，则会采用注解值里面name的属性值的参数发送邮件
     */
    @Email(name = "EmailOffice365")
    @PostMapping("/mime/send")
    public BaseResult<Void> sendMimeEmail(@ModelAttribute(name = "message") @Validated MimeMessageDomain message,
                                          @RequestPart(name = "file", required = false) MultipartFile file
    );
```

此方法有标注`@Email(name = "EmailOffice365")`注解指定邮件模板，指定以配置文件`lejing-common/lejing-common-email-support/src/main/resources/application-email.yml`里面的`EmailOffice365`邮件模板发送，在处理逻辑是会调用对应的对应的`JavaMailSender`实例执行发短信的逻辑。

### 1.7 效果演示

![image-20210910233435972](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210910233435972.png)

你可申请好对应的邮件配置信息后，启动**LejingSiteReserveApplication**访问里面的index文件查看效果。

**LejingSiteReserveApplication**的最小基础软件配置：

- nocos
- mysql

参数示例：

![image-20210910233858832](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210910233858832.png)



## 2 总结&提示

**1.3**配置完后，意味着**lejing-site-reserve**这个服务已经整合了多模板邮件发送功能。

关于**spring.profiles.active=dev**和**spring.profiles.include=email**的加载顺序：

- 后者在**spring**启动的时候会优先加载**spring.profiles.include=email**里面的邮件配置元数据
- 然后再加载**spring.profiles.active=dev**的元数据
- **spring.profiles.include**引入元数据会覆盖当前服务的同名属性

