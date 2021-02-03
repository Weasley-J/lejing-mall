![shanghao-idea](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/shanghao-idea.jpg)

# 乐璟商城

# 1 技术架构选型

第二代微服务架构，kubernetes高可用集群，全自动DevOps，

[springcloud与springboot对应版本](https://start.spring.io/actuator/info)

![image-20210126234300478](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234300478.png)

[springboot的历史版本](https://docs.spring.io/spring-boot/docs/)

![image-20210126234531221](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210126234531221.png)

综合上述调研，我最终为乐景商城最终选取的技术架构是

- JDK11

- MySQL-8.0.23

- apache-maven-3.6.3

- springboot 2.2.9.RELEASE

- spring-cloud-alibaba 2.2.1.RELEASE

# 2 已完成清单

- [x] 分装好BaseController，用于Controller数据返回

![image-20210204005654609](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204005654609.png)

- [ ] 分装好BaseResult，返回结果封装 ，让接口的输入和输出更加明确

![image-20210204010657082](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010657082.png)

![image-20210204010822582](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010822582.png)



![image-20210204010952249](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210204010952249.png)



# 2 待完成清单

- [ ] 整合人人框架，提供：domain--> dao --> service --> controller --> 全自动api文档在线输出**&**在线调试，

  目的：让今后因业务变动影响数据库的变动只需要码农修改数据库对应的领域模型即可，节省更多的时间