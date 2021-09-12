# `lejing-job`快速开始指南

> 此模块和`lejing`其他模块一样均采用`spring-boot-starter-xxx`或者`xxx-spring-boot-starter`直接集成一些优秀的开源组件，严禁手动造车，如果你造的车比`spring-boot-starter-xxx`还好，这种情况除外。
>
> 
>
> 此模块的核心依赖为:
>
> ```xml
> <!-- quartz -->
> <dependency>
>  <groupId>org.springframework.boot</groupId>
>  <artifactId>spring-boot-starter-quartz</artifactId>
> </dependency>
> ```



## 1 学习指南

1. [spring-boot-starter-quartz链接](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#features.quartz)

2. [quartz官方指南](http://www.quartz-scheduler.org/)



## 2 前置准备

1. 初始化数据库

数据库有两部分组成：`Quartz`（`11`张表，`QRTZ_`前缀开始） + `job`（两张表,`quartz_`前缀开始）

见资源文件：`sql/lejing_job.sql`



> 初始化数据库是应注意：
>
> - Linux环境严格区分大小写
> - Windows环境大小写不明感



2. 配置`spring.quartz`前缀的元配置数据

> 要契合你的业务属性

以下配置数据摘自资源文件：`lejing-job/src/main/resources/application-dev.yml`

```yaml
spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.40.132:3306/lejing_job?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true&allowMultiQueries=true
    username: root
    password: 123456

  #quartz任务调度配置
  quartz:
    job-store-type: jdbc
    jdbc:
      #初始化Quartz表结构,项目第一次启动配置程always, 然后改成never, 否则已生成的job会被初始化掉
      initialize-schema: never
      comment-prefix: QRTZ_
    scheduler-name: "${spring.application.name:Lejing}-Quartz-Scheduler"
    startup-delay: 0s
    auto-startup: true
    wait-for-jobs-to-complete-on-shutdown: true
    overwrite-existing-jobs: true
    # 额外的Quartz调度器属性
    properties:
      org:
        quartz:
          scheduler:
            #调度器的实例名
            instanceName: MyClusteredScheduler
            #调度器编号自动生成
            instanceId: AUTO
          jobStore:
            class: org.quartz.impl.jdbcjobstore.JobStoreTX
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            #数据库表名前缀
            tablePrefix: QRTZ_
            #开启分布式部署
            isClustered: true
            #分布式节点有效性检查时间间隔,单位:秒
            clusterCheckinInterval: 10000
            useProperties: false
          threadPool:
            #自带的线程池实现类
            class: org.quartz.simpl.SimpleThreadPool
            #开启15个线程
            threadCount: 15
            #工作者线程的优先级
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
```



## 3 Key Point

- `quartz`的3大核心对象



- `cn.alphahub.mall.schedule.core.service.QuartzCoreService`接口介绍



- 自定义`JSR303` `cron`表达式校验注解`@Cron`介绍

