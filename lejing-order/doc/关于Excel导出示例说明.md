# 关于Excel导出示例说明

## 1 项目结构

![image-20210721215516991](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210721215516991.png)

在自己的项目的`pom.xml`按需引入引入主流的Excel处理工具，实际项目中不推荐两者同时引用，二选一即可。

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

![image-20210721215907874](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210721215907874.png)



**web文件上传下载直接copy**



## 3 已知问题

* `easyexcel`和`Lombok`使用，当在`excel`实体类上标注`@Accessors(chain = true)`时，`easyexcel`存在严重bug，读取到的属性值全为null。
* `easyexcel`和`easypoi`的不能同时混用，否则存在poi的jar包冲突。

此种情况使用一种即可，`pom`里面要么使用`easypoi`，要么使用`easyexcel`

![image-20210710220432380](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210710220432380.png)

## 4 使用感受

众所周知，`lombok`的推出使`java`变得更简洁，项目中一般不会写大量的getter和setter，鉴于`easyexcel`和`Lombok`使用，当在`excel`实体类上标注`@Accessors(chain = true)`时，`easyexcel`存在严重bug导致读取`excel`实体类的属性值全为null，个人更推荐`easypoi`在项目中使用，可定制性、可拓展性比`easyexcel`好，我已经做好和`springboot`整合的代码，游客可以直接copy到项目中使用，最后提示下，实际项目中二选一即可。