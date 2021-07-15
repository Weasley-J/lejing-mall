# 关于Excel导出示例说明

## 1 `lejing-order-service`的pom.xml同时引入主流的Excel处理工具

实际项目中不推荐两者同时引用，二选一即可。

```xml
<!-- easyexcel -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
</dependency>
<!-- easypoi -->
<dependency>
    <groupId>cn.afterturn</groupId>
    <artifactId>easypoi-spring-boot-starter</artifactId>
</dependency>
```

## 2 增加各自的使用示例

![image-20210710215905713](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210710215905713.png)

web文件上传下载直接copy

## 3 已知问题

* easyexcel和Lombok使用，当在excel实体类上标注`@Accessors(chain = true)`时，easyexcel存在严重bug，读取到的属性值全为null。
* easyexcel和easypoi的不能同时混用，否则存在poi的jar包冲突。

此种情况使用一种即可，pom里面要么使用easypoi，要么使用easyexcel

![image-20210710220432380](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210710220432380.png)

## 4 使用感受

众所周知，lombok的推出使java变得更简洁，项目中一般不会写大量的getter和setter，鉴于easyexcel和Lombok使用，当在excel实体类上标注`@Accessors(chain = true)`时，easyexcel存在严重bug导致读取excel实体类的属性值全为null，故我个人更推荐easypoi在项目中使用，可定制性、可拓展性比easyexcel好，我已经做好和springboot整合的代码，可以直接copy到项目中使用，最后提示下，实际项目中二选一即可。