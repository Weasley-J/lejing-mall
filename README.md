![shanghao-idea](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/shanghao-idea.jpg)

#                                                   乐璟商城

> **微服务架构图**

![](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210222191025177.png)

# 1 技术架构选型

第二代微服务架构，`kubernetes`高可用集群，全自动`DevOps`，

[springcloud与springboot对应版本](https://start.spring.io/actuator/info)

![image-20210126234300478](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234300478.png)

[spring boot的历史版本](https://docs.spring.io/spring-boot/docs/)

![image-20210126234531221](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234531221.png)

[spring cloud的历史版本](https://docs.spring.io/spring-cloud/docs/)

![image-20210224205205376](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210224205205376.png)

技术架构：

- `JDK11`
- `MySQL-8.0.23`
- `apache-maven-3.6.3`
- `spring-boot 2.3.9.RELEASE`
- `spring-cloud-Hoxton.SR10`
- `spring-cloud-alibaba 2.2.5.RELEASE`

**架构图**



# 2 IDEA环境JVM参数配置

> tips: 主要目的减少web服务的的内存占用

JVM参数: `-Xms512m -Xmx512m -Xmn200m -Xlog:gc*`

![image-20210207215344410](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210207215344410.png)

![image-20210207215556800](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210207215556800.png)



# 3 已完成清单

- [x] 已封装`BaseController`，用于Controller数据返回

![image-20210204005654609](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204005654609.png)

- [x] 已封装`BaseResult`，返回结果封装 ，让接口的输入和输出更加明确

![image-20210204010657082](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010657082.png)

![image-20210204010822582](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010822582.png)



![image-20210204010952249](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010952249.png)

- [x] 重新改造代码生成, 所有接口都可以生成测试文档, 支持在线调试接口, 执行`mvn package`自动出api文档,访问地址改成自己的端口号:http://127.0.0.1:10000/debug-all.html,

  ![image-20210205025359610](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025359610.png)

![image-20210205024651157](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205024651157.png)

> 基于`Mybatis-Plus`自动生成,支持复杂分页查询, 数据库变动只需要修改实体类即可

![image-20210205025122573](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025122573.png)

![image-20210205025022332](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025022332.png)

**![image-20210205024941585](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205024941585.png)**

- [x] 项目改造完成, 集成: 业务代码自动生成 -> 执行: `mvn package`, 自动输出可调式的高可读性`api`文档 -> 分层架构 -> 以最小的代价迎合业务的重大变更

- [x] 已接入`ELK`全家桶


![image-20210307003402307](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210307003402307.png)

![image-20210307003417433](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210307003417433.png)



# 4 待完成清单

- [x] 整合人人框架，提供：`domain`--> `dao` --> `service` --> `controller` --> 全自动`api`文档在线输出, 支持在线调试

  目的：让今后因业务变动影响数据库的变动只需要码农修改数据库对应的领域模型即可，节省更多的时间

- [ ] 微服务计划

进行中......



- [x] 移除`spring-cloud-alibaba 2.2.5.RELEASE` 版本中微服务中通过`feign`远程调用时使用`ribbon`负载均衡的支持, 采用`spring-cloud 2020.0.1`的`spring cloud loadbalancer`代替



# 5 `Ubuntu`环境`Docker`基础软件安装指南

## 5.1 安装ELK全家桶

1. 下载本项目

```shell
git clone https://github.com/Weasley-J/lejing-mall
```

2. 切换工作目录

```shell
cd shell
chmod 0777 -vR *.sh && ./run_elk_install.sh
```

## 5.2 修改`logback-spring.xml`中`logstash`的参数

![image-20210303224138777](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210303224138777.png)

```xml
<destination>192.168.40.132:5044</destination>
```

以上标签值改为你自己的 `Logstash` 能接受log的主机`ip:port`

## 5.3 `Logstash` 成功接受日志的效果

1. 终端

![image-20210303224508305](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210303224508305.png)

2. es中的索引数据

![image-20210303224559097](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210303224559097.png)

![image-20210303224642551](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210303224642551.png)

# 6 TIPS

> 本项目的`api`文档输出基于smart-doc和common工程里面我写的一些类完成的，不建议把common工程上传到maven私服上面，这会导致项目执行：
>
> `mvn package`的时候不能读取common里面的一些相关类的注释，因为`java`最终编译成字节码文件后会把所有注释都清理掉，也就是项目最终的class文件不包含任何注释

## 6.1 `nacos MySQL`数据持久配置

文件相对路径：`nacos-server/nacos-server-1.4.1/nacos/conf/application.properties`

```properties
### If use MySQL as datasource:
spring.datasource.platform=mysql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:mysql://192.168.40.132:3306/nacos_config?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true&allowMultiQueries=true
db.user.0=root
db.password.0=123456
```

## 6.2 各个服务启动顺序

- 启动`nacos`，进入终端：`nacos-server/nacos-server-1.4.1/nacos/run-nacos-standalone.bat`
- 启动授权服务
- 启动网关服务 `lejing-gateway`
- 再启动其他相关服务



## 6.3 `Elasticsearch`索引库清理

使用`curl`命令模糊匹配删除，在`shell`终端上执行:

```shell
#!/usr/bin/env bash

# 全局变量，自行修改
IP="192.168.40.132"
PORT="9200"
USER="elastic"
PWD="123456"

# 删除乐景商城的日志索引库
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/lejing-*,renren-fast-*

# 删除kibana的日志索引库
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/.monitoring-kibana-*

# 删除logstash的日志索引库
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/.monitoring-logstash-*

# 删除es的日志索引库
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/.monitoring-es-*

```



## 6.4  前端项目启动

### 6.4.1 前端后台管理

```shell
#克隆项目，见lejing-web-frontend/lejing-manage/README.md文件
```



### 6.4.2 前端门户网站

前端多页面`html`使用`live-server`运行：

```shell
#打开CMD终端
cd lejing-web-frontend/lejing-portal
# npm下载live-server启动门户网站, 指定门户网站的端口8002
npm install -g live-server --registry=https://registry.npm.taobao.org && live-server --port=8002
```



# 7 Q&A

## 7.1 为什么分页不用`mybatis-plus`自带的`IPage`？

`mybatis-plus`的`IPage`分页入参不利于项目的`API`文档输出，毕竟我们都不想手写接口文档，如果你喜欢手写接口文档，然后再填入什么`RAP`、`YApi`里面，我推荐你用回`Swagger`。
