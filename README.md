![shanghao-idea](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/shanghao-idea.jpg)

# 乐璟商城

# 1 技术架构选型

第二代微服务架构，kubernetes高可用集群，全自动DevOps，

[springcloud与springboot对应版本](https://start.spring.io/actuator/info)

![image-20210126234300478](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234300478.png)

[springboot的历史版本](https://docs.spring.io/spring-boot/docs/)

![image-20210126234531221](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234531221.png)

综合上述调研，最终为乐景商城最终技术架构

- JDK11
- MySQL-8.0.23
- apache-maven-3.6.3
- springboot 2.3.8.RELEASE
- spring-cloud-alibaba 2.2.5.RELEASE



# 2 IDEA环境JVM参数配置

> tips: 主要目的减少web服务的的内存占用

JVM参数: `-Xms512m -Xmx512m -Xmn200m -Xlog:gc*`

![image-20210207215344410](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210207215344410.png)

![image-20210207215556800](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210207215556800.png)



# 3 已完成清单

- [x] 分装好BaseController，用于Controller数据返回

![image-20210204005654609](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204005654609.png)

- [x] 分装好BaseResult，返回结果封装 ，让接口的输入和输出更加明确

![image-20210204010657082](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010657082.png)

![image-20210204010822582](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010822582.png)



![image-20210204010952249](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010952249.png)

- [x] 重新改造代码生成, 所有接口都可以生成测试文档, 支持在线调试接口, 执行`mvn package`自动出api文档,访问地址改成自己的端口号:http://127.0.0.1:10000/debug-all.html,

  ![image-20210205025359610](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025359610.png)

![image-20210205024651157](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205024651157.png)

> 基于Mybatis-Plus自动生成,支持复杂分页查询, 数据库变动只需要修改实体类即可

![image-20210205025122573](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025122573.png)

![image-20210205025022332](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205025022332.png)

**![image-20210205024941585](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210205024941585.png)**

- [x] 项目改造完成,集成:业务代码自动生成->执行mvn package,自动输出可调式的高可读性api文档->分层架构->以最小的代价迎合业务的重大变更





# 4 待完成清单

- [x] 整合人人框架，提供：domain--> dao --> service --> controller --> 全自动api文档在线输出**&**在线调试，

  目的：让今后因业务变动影响数据库的变动只需要码农修改数据库对应的领域模型即可，节省更多的时间

- [ ] 微服务计划