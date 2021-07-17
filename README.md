![shanghao-idea](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/shanghao-idea.jpg)

#                                                   乐璟商城

> **微服务架构图**

![](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210222191025177.png)

> `SonarQube`代码扫描结果（不含前端代码）

![image-20210718023454451](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210718023454451.png)



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
- `MySQL-8.0.25`
- `apache-maven-3.8.1`
- `spring-boot 2.5.2`
- `spring-cloud-2020.0.3`
- `spring-cloud-alibaba 2021.1`

**架构图**



# 2 IDEA环境JVM参数配置

> tips: 主要目的减少web服务的的内存占用

JVM参数: `-Xms256m -Xmx256m -Xmn100m -Xlog:gc*`

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

- [ ] 整合人人框架，提供：`domain`--> `dao` --> `service` --> `controller` --> 全自动`api`文档在线输出, 支持在线调试

  目的：让今后因业务变动影响数据库的变动只需要码农修改数据库对应的领域模型即可，节省更多的时间

- [ ] 微服务计划

进行中......



- [ ] 移除`spring-cloud-alibaba 2.2.5.RELEASE` 版本中微服务中通过`feign`远程调用时使用`ribbon`负载均衡的支持, 采用`spring-cloud 2020.0.1`的`spring cloud loadbalancer`代替

- [ ] ......



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
# 其他
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/ilm-history-*
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/.kibana-event-*
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/*run_env*
curl -XDELETE -u ${USER}:${PWD} http://${IP}:${PORT}/.kibana_*
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



## 6.5  集成`SonarQube`

- 项目父`pom.xml`添加maven插件

版本自行取中央仓库查找

```xml
           <!-- https://mvnrepository.com/artifact/org.sonarsource.scanner.maven/sonar-maven-plugin -->
            <plugin>
                <groupId>org.sonarsource.scanner.maven</groupId>
                <artifactId>sonar-maven-plugin</artifactId>
                <version>${sonar-maven-plugin.version}</version>
            </plugin>
```

- maven的`settings.xml`新加如下配置

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <pluginGroups>
          <pluginGroup>org.sonarsource.scanner.maven</pluginGroup>
      </pluginGroups>
      
      <mirrors>
          <mirror>
              <id>aliyunmaven</id>
              <mirrorOf>central</mirrorOf>
              <name>阿里云公共仓库</name>
              <url>https://maven.aliyun.com/repository/public</url>
          </mirror>
      </mirrors>
  
      <profiles>
          <profile>
              <id>sonar</id>
              <activation>
                  <activeByDefault>true</activeByDefault>
              </activation>
              <properties>
                  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                  <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
                  <sonar.host.url>http://192.168.40.132:9001</sonar.host.url>
                  <sonar.login>d65fc87408fd94ef8328b03a52d06bfec0fe388f</sonar.login>
                  <!-- 排除的文件 -->
                  <sonar.exclusions>*.js</sonar.exclusions>
                  <sonar.exclusions>*.bat</sonar.exclusions>
                  <sonar.exclusions>*.cmd</sonar.exclusions>
                  <sonar.exclusions>*.xml</sonar.exclusions>
                  <sonar.exclusions>*.md</sonar.exclusions>
                  <sonar.exclusions>*.NET</sonar.exclusions>
                  <sonar.exclusions>*.cpp</sonar.exclusions>
                  <sonar.exclusions>*.css</sonar.exclusions>
                  <sonar.exclusions>*.html</sonar.exclusions>
                  <sonar.exclusions>*.vue</sonar.exclusions>
                  <sonar.exclusions>src/test/**</sonar.exclusions>
              </properties>
          </profile>
      </profiles>
  
      <activeProfiles>
          <activeProfile>sonar</activeProfile>
      </activeProfiles>
  
  </settings>
  ```

- docker安装`SonarQube`

```shell
#!/bin/bash

BASE_DIR_POSTGRES="/usr/local/postgres"
BASE_DIR_SONARQUBE="/usr/local/sonarqube"

#主机ip
POSTGRESQL_HOST="192.168.40.132"

clear && rm -rfv ${BASE_DIR_POSTGRES} && rm -rfv ${BASE_DIR_SONARQUBE}
mkdir -pv ${BASE_DIR_POSTGRES} && mkdir -pv ${BASE_DIR_SONARQUBE}/{data,extensions,logs}
chown -vR 0999 ${BASE_DIR_SONARQUBE}/

docker stop postgres && docker rm -f postgres
docker run --name postgres \
  -p 5432:5432 \
  -v ${BASE_DIR_POSTGRES}:/var/lib/postgresql/data \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -e POSTGRES_USER=root \
  -e POSTGRES_DB=postgres \
  -e POSTGRES_PASSWORD=123456 \
  -e PGDATA=/var/lib/postgresql/data/pgdata \
  -d postgres

# 开启postgresql远程访问参考连接: https://www.cnblogs.com/chendongbky/p/14874794.html, 看5 -> 5.(1)

docker exec -it postgres psql -h ${POSTGRESQL_HOST} -U root
# 界面运行下面的SQL, 创建sonarqube的数据库
CREATE DATABASE "sonarqube" WITH OWNER = "root" TEMPLATE = "postgres" ENCODING = 'UTF8' TABLESPACE = "pg_default";
COMMENT ON DATABASE "sonarqube" IS 'SonarQube数据库';

#退出容器

# 社区版本
docker stop sonarqube && docker rm -f sonarqube
docker run --name sonarqube \
  -p 9001:9000 \
  -p 9092:9092 \
  -e SONAR_JDBC_USERNAME=root \
  -e SONAR_JDBC_PASSWORD=123456 \
  -e SONAR_JDBC_URL="jdbc:postgresql://${POSTGRESQL_HOST}:5432/sonarqube" \
  -v ${BASE_DIR_SONARQUBE}/data:/opt/sonarqube/data \
  -v ${BASE_DIR_SONARQUBE}/extensions:/opt/sonarqube/extensions \
  -v ${BASE_DIR_SONARQUBE}/logs:/opt/sonarqube/logs \
  -d sonarqube

# 企业版本
#docker run -d -p 9001:9000  -p 9092:9092 --name sonarqube \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_data:/opt/sonarqube/data \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_extensions:/opt/sonarqube/extensions \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_logs:/opt/sonarqube/logs \
#-e SONAR_JDBC_USERNAME=sonarqube \
#-e SONAR_JDBC_PASSWORD=sonarqube \
#-e SONAR_JDBC_URL="jdbc:postgresql://${POSTGRESQL_HOST}:5432/sonarqube" \
#sonarqube:enterprise

#编辑这个文件, 再末尾追加(已经有就不用追加): vm.max_map_count=655360
vim /etc/sysctl.conf
sysctl -p

# echo后边用单引号包围要添加的内容
echo 'vm.max_map_count=655360' >>/etc/sysctl.conf
sysctl -p

# 浏览器访问sonarqube: http://192.168.40.132:9001, 默认用户名密码为：admin/admin , 进入界面后: Administration --> marketplace --> 搜索chinese, 安装中文语言

# token - > 99bfe61d5bba8bb2164d6679ee345c03ad30d5d7

```



# 7 Q&A

## 7.1 为什么分页不用`mybatis-plus`自带的`IPage`？

`mybatis-plus`的`IPage`分页入参不利于项目的`API`文档输出，毕竟我们都不想手写接口文档，如果你喜欢手写接口文档，然后再填入什么`RAP`、`YApi`里面，我推荐你用回`Swagger`。

## 7.2 解决`github`提交代码`connection to github.com:443`

`fatal: unable to access ‘https://github.com/Weasley-J/lejing-mall’: OpenSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443`

在Git Bash终端中输入:

```bash
env GIT_SSL_NO_VERIFY=true
```

## 7.3 项目启动报错`Web server failed to start. Port 8000 was already in use.`

> windows

```bat
netstat -aon | findstr "10000"

tasklist | findstr "1532"

taskkill /f /t /im "yundetectservice.exe"
```

![image-20210611221759994](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210611221759994.png)

![image-20210611221811205](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210611221811205.png)



## 7.4  关于前端查询数据需要根据时间区间查询时`spring`全局`mvc`字符串日期转换配置

![image-20210623234349087](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210623234349087.png)

笔者亲身经历，土鳖公司当查询参数过多的时候就用POST，实在是很不习惯。所有就这个全局配置，当然不止这2种方式，有兴趣自行探索。



# 8 接入三方支付

## 8.1 支付宝支付-2021年6月20日

### 8.1.1 [个人开发者申请登录后台](https://open.alipay.com/platform/developerIndex.htm)

![image-20210620223425913](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210620223425913.png)



### 8.1.2 [新版`api`文档](https://opendocs.alipay.com/open/54/00y8k9)

![image-20210620223644859](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210620223644859.png)

### 8.1.3  `nginx`配置支付宝支付成功异步POST回调

```nginx
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

pid        logs/nginx.pid;

events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    #解决"nginx could not build the server_names_hash"的方法
    server_names_hash_bucket_size 64;

    client_max_body_size 4G;


	#乐璟商城
    server {
        listen       80;
        
        server_name  lejing.fgifast1.vipnps.vip;#内网穿透域名地址

        proxy_ssl_verify off;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header REMOTE-HOST $remote_addr;
        proxy_set_header Host $http_host;
        proxy_set_header cookie $http_cookie;
        proxy_set_header Proxy-Connection "";
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
		# 接受支付宝支付成功异步回调
        location /payed/ {
		   proxy_set_header Host order.lejing.com;#把内网穿透过来的请求改为order.lejing.com;
           proxy_pass http://order.lejing.com/payed/;#接受支付宝异步通知的POST接口
        }
		
        location / {
           proxy_pass http://localhost:88/api;
        }

        error_page  404              /404.html;

        error_page   500 502 503 504  /50x.html;
        
        location = /50x.html {
            root   html;
        }
    }
}
```

# 9 增加目前主流的web excel处理示例

详见：`lejing-order/doc/关于Excel导出示例说明.md`

![image-20210710221552613](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210710221552613.png)

## 9.1 大数据量excel文件导出，`5W~100W`数据量



## 9.2 excel文件预览



# 10 接入`Torna` `API`文档管理工具，完美融合`smart-doc`

> 全程无需手写文档，执行maven插件即可
>
> ![image-20210717232437120](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210717232437120.png)



文档效果：

![image-20210717230458352](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210717230458352.png)

![image-20210717230515865](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210717230515865.png)

## 10.1 `SQL`文件: `sql/torna.sql`



## 10.2 安装shell脚本: `shell/run_torna_install.sh`



