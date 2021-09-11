# 代码生成服务



> 基于人人开源项目二次开发，做了大量提升优化：
>
> 1. 集成`nacos`配置中心
> 2. 支持**不重启代码生成服务**动态刷新`com.zaxxer.hikari.HikariDataSource`数据连接池更换所需生成代码的数据库，意味着你可以**不重启服务**动态切换任意数据库:`MySQL` -> `MySQL`, `MySQL` -> `Oracle`, `Oracle`-> `PostgreSQL`, `PostgreSQL` -> `MySQL`, ...
>
> 3. 支持**不重启代码生成服务**动态刷新数据库字段和`java`基础包装类型的映射关系，见配置文件：`generator.properties`



## 核心配置

资源结构：

![image-20210911233940569](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210911233940569.png)



- `bootstrap.yml`

1. 从配置中心加载配置文件元数据
2. 提前去`nacos`中的创建对应的命名空间，配置信息参看如下：

```yaml
spring:
  application:
    name: lejing-generator
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        group: DEFAULT_GROUP
        namespace: b3bf69ba-f696-4479-aae1-94807a56eb90
        refresh-enabled: true
        file-extension: yml
        shared-configs:
          - application.yml
        extension-configs:
          - data-id: datasource.yml
            group: DEFAULT_GROUP
            refresh: true
          - data-id: generator.properties
            group: DEFAULT_GROUP
            refresh: true
```

> 说明：
>
> （1）`spring-cloud-alibaba-starters`版本：2021.1
>
> （2）`nacos`版本: 2.0.3
>
> 参考图：
>
> ![image-20210911235730813](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210911235730813.png)
>
> ![image-20210911235851616](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210911235851616.png)
>
> 完成以上操作，你的`nacos`已经配置好了, 接下来你只需要动态变更`datasource.yml`和`generator.properties`里面的配置数据即可



- `datasource.yml`

1. 做为`nacos`配置文件的`dataId`动态切换数据库

```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.40.132:3306/lejing_job?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
#指定要生成代码的数据库类型, 可选值: MYSQL, ORACLE, SQLSERVER, POSTGRESQL, MONGODB
code:
  generator:
    db-type: MYSQL

#mongodb:
#  host: localhost
#  port: 27017
#  auth: false #是否使用密码验证
#  username: admin
#  password: 123456
#  source: 123456
#  database: mongodb_test

```



- `generator.properties`

```properties
#
# 代码生成配置信息
#
#域名|组织名取反
mainPath=cn.alphahub
#包名
package=cn.alphahub.mall
#模块名称
moduleName=sys
#作者名字
author=Weasley J
#作者邮箱
email=1432689025@qq.com
#表前缀(指定表前缀生成代码的java类名不会包含表前缀, 不指定表前缀表示类名包含表前缀, 如: sys_)
tablePrefix=
#代码生成后下载的zip包名称
codeZipFileName=lejing-job
#数据库的数据类型给与Java的数据类型对应关系
enum=String
tinyint=Integer
smallint=Integer
mediumint=Integer
int=Integer
integer=Integer
bigint=Long
float=Float
double=Double
decimal=BigDecimal
bit=Boolean
char=String
varchar=String
tinytext=String
text=String
mediumtext=String
longtext=String
longblob=String
datetime=LocalDateTime
timestamp=LocalDateTime
date=LocalDate
time=LocalTime
NUMBER=Integer
INT=Integer
INTEGER=Integer
BINARY_INTEGER=Integer
LONG=String
FLOAT=Float
BINARY_FLOAT=Float
DOUBLE=Double
BINARY_DOUBLE=Double
DECIMAL=BigDecimal
CHAR=String
VARCHAR=String
VARCHAR2=String
NVARCHAR=String
NVARCHAR2=String
CLOB=String
BLOB=String
DATE=LocalDateTime
DATETIME=LocalDateTime
TIMESTAMP=LocalDateTime
TIMESTAMP(6)=LocalDateTime
int8=Long
int4=Integer
int2=Integer
numeric=BigDecimal
nvarchar=String

```

这个配置文件注释很细，相信你一看就懂，不做过多说明



## **项目说明** 

- 在线生成`domain`、`xml`、`mapper`、`service`、`controller`、前端`vue`文件、`js`、`sql`代码，减少70%以上的开发任务, 通常情况下删除生成的`controller`文件, 然后根据自己的业务场景编写对应的业务接口，持久层基于基于`mybatis-plus`。
- 整合`smart-doc`，执行项目的 `mvn package` 可直接输出`Restful api`，支持调试，你可能需要在你的项目中引入`lejing-common/lejing-common-base-public`模块和配置`smart-doc.json`文件，`smart-doc`学习地址：https://gitee.com/smart-doc-team/smart-doc，这是一个不错的`api`文档生成工具。

![image-20210912002146693](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210912002146693.png)



- 生成的接口：获取`xx`分页列表、获取`xx`详情、保存`xx`、修改`xx`、批量删除`xx`

![image-20210228214229671](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214229671.png)



![image-20210228214348571](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210228214348571.png)



## **本地部署**

- 通过git下载源码:

```bash
git clone https://github.com/Weasley-J/lejing-mall
```

- 安装配置好`nacos`

1. 创建对应的命名空间，直接从项目文件`lejing-generator/src/main/resources/bootstrap.yml`里面复制对应的参数即可。

![image-20210912000740199](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210912000740199.png)

2. 创建命名空间对应的配置文件，元数据直接从`lejing-generator/src/main/resources/`下面复制，改成自己的业务数据库。

![image-20210912001057849](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210912001057849.png)

- 修改`application.yml`，更新`MySQL`账号和密码、数据库名称，修改成你自己的业务数据库连接配置。

```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.40.132:3306/lejing_job?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
#指定要生成代码的数据库类型, 可选值: MYSQL, ORACLE, SQLSERVER, POSTGRESQL, MONGODB
code:
  generator:
    db-type: MYSQL

#mongodb:
#  host: localhost
#  port: 27017
#  auth: false #是否使用密码验证
#  username: admin
#  password: 123456
#  source: 123456
#  database: mongodb_test

```

- 修改`generator.properties`文件，重点修改下面这几项：

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
#表前缀(指定表前缀生成代码的java类名不会包含表前缀, 不指定表前缀表示类名包含表前缀, 如: sms_)
tablePrefix=sms_
#代码生成后下载的zip包名称
codeZipFileName=lejing-coupon
# ...
```

- Eclipse、IDEA运行`CodeGeneratorApplication.java`，则可启动项目
- 项目访问路径：http://localhost:8080



**演示效果图：**
![image-20210912001738403](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210912001738403.png)
