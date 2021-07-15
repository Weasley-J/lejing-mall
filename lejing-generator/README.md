# 代码生成器

> 基于人人开源项目二次开发，做了大量优化

## 1 **项目说明** 

- 可在线生成domain、xml、mapper、service、controller、html、js、sql代码，减少70%以上的开发任务
- 整合smart-doc，执行项目的 `mvn package` 可直接输出Restful api，支持调试

![image-20210228214456831](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214456831.png)

- 生成的接口：获取xx分页列表、获取xx详情、保存xx、修改xx、批量删除xx

![image-20210228214229671](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214229671.png)

![image-20210228214309598](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214309598.png)

![image-20210228214348571](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214348571.png)

## 2 **本地部署**

- 通过git下载源码
- 修改application.yml，更新MySQL账号和密码、数据库名称

```yaml
spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.40.132:33306/lejing_sms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

- 修改generator.properties文件，重点修改下面这几项：

```properties
#
# 代码生成配置信息
#
#域名|组织名取反
mainPath=cn.alphahub
#包名
package=cn.alphahub.mall
#模块名称
moduleName=coupon
#作者
author=Weasley J
#email
email=1432689025@qq.com
#表前缀(类名不会包含表前缀)
tablePrefix=sms_
#代码生成后下载的zip包名称
codeZipFileName=lejing-coupon
```



- Eclipse、IDEA运行CodeGeneratorApplication.java，则可启动项目
- 项目访问路径：http://localhost

**演示效果图：**
![image-20210228212825348](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228212825348.png)
