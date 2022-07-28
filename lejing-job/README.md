# `lejing-job`快速开始指南

> 此模块和`lejing`其他模块一样均采用`spring-boot-starter-xxx`或者`xxx-spring-boot-starter`直接集成一些优秀的开源组件，严禁手动造车，如果你造的车比`spring-boot-starter-xxx`还好，这种情况除外。
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

1. `org.quartz.Scheduler`
2. `org.quartz.JobDetail`
3. `org.quartz.Trigger`

如果你还没了解这3个对象请参阅学习指南。

- `cn.alphahub.mall.schedule.core.service.QuartzCoreService`接口介绍

> 此接口提封装了`quartz`的常用的模板方法

```java
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.List;
import java.util.Objects;

import static cn.alphahub.mall.schedule.constant.ScheduleConstant.MisfireHandling;

/**
 * quartz任务调度核心方法上层接口
 *
 * @author lwj
 * @version 1.0.1
 */
public interface QuartzCoreService {
    /**
     * 任务名称前缀
     */
    String JOB_NAME_PREFIX = "SCHEDULE_TASK_";

    /**
     * 创建Simple定时任务
     * <p> param.getInterval()==null表示单次提醒，否则循环提醒（param.getEndTime()!=null）</p>
     *
     * @param param Quartz参数实体类
     * @return true：成功，false：失败
     */
    boolean createSimpleScheduleJob(QuartzParam param);

    /**
     * 创建Cron定时任务
     * 定时任务创建之后默认启动状态
     *
     * @param param 定时任务信息类
     * @return true：成功，false：失败
     */
    boolean createCronScheduleJob(QuartzParam param);

    /**
     * 暂停定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean pauseScheduleJob(String jobName, String jobGroup);

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  定时任务名
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean resumeScheduleJob(String jobName, String jobGroup);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     */
    void updateSimpleScheduleJob(QuartzParam param);

    /**
     * 更新Cron定时任务
     *
     * @param param 定时任务信息类
     * @return true：成功，false：失败
     */
    boolean updateCronScheduleJob(QuartzParam param);

    /**
     * 从调度器当中删除定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败@return true：成功，false：失败
     */
    boolean deleteScheduleJob(String jobName, String jobGroup);

    /**
     * 获取任务状态
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return BLOCKED-阻塞;
     * COMPLETE-完成;
     * ERROR-出错;
     * NONE-不存在;
     * NORMAL-正常;
     * PAUSED-暂停;
     */
    String getScheduleJobStatus(String jobName, String jobGroup);

    /**
     * 获取任务状态(quartz原生状态)
     *
     * @param param 定时任务元数据类
     * @return Trigger.TriggerState
     * @throws SchedulerException scheduler exception
     */
    Trigger.TriggerState getScheduleJobStatus(QuartzParam param);

    /**
     * 判断任务是否存在
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean isScheduleJobExists(String jobName, String jobGroup);

    /**
     * 根据任务組刪除定時任务
     *
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    boolean deleteGroupJob(String jobGroup);

    /**
     * 根据任务組批量刪除定時任务
     *
     * @param jobKeyList job key list
     * @return true：成功，false：失败
     */
    boolean batchDeleteGroupJob(List<JobKey> jobKeyList);

    /**
     * 根据任务組批量查询出jobKey
     *
     * @param jobGroup   任务组
     * @param jobKeyList job key list
     */
    void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup);

    /**
     * 立即运行一次定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    boolean executeAtNow(String jobName, String jobGroup);

    /**
     * 暂停全部任务
     */
    void pauseAll() throws SchedulerException;

    /**
     * 恢复全部任务
     */
    void resumeAll() throws SchedulerException;

}
```

你可以结合你的业务表`quartz_job`编写任务的`CRUD`，注意持久层任务状态要和`quartz`任务调度工厂的保持一致，具体实现代码见`lejing-job`实现类：`cn.alphahub.mall.schedule.job.service.impl.QuartzJobServiceImpl`(`quartz`定时任务调度Service业务层处理)，以下是Service上层接口代码，见具体实现。

```java
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * quartz定时任务调度Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
public interface QuartzJobService extends IService<QuartzJob> {

    /**
     * 查询quartz定时任务调度分页列表
     *
     * @param page      分页参数
     * @param quartzJob 分页对象
     * @return quartz定时任务调度分页数据
     */
    PageResult<QuartzJob> queryPage(PageDomain page, QuartzJob quartzJob);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     * @return result
     */
    Result<Boolean> createSimpleScheduleJob(QuartzParam param);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     * @return result
     */
    Result<Boolean> updateSimpleScheduleJob(QuartzParam param);

    /**
     * 新增定时任务(创建->启动定时任务)
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    Result<Boolean> save(QuartzJobDTO job);

    /**
     * 获取quartz定时任务调度详情
     *
     * @param id quartz定时任务调度主键id
     * @return quartz定时任务调度详细信息
     */
    Result<QuartzJobDTO> info(Long id);

    /**
     * 删除定时任务
     *
     * @param ids 定时任务id集合
     * @return void
     */
    Result<Void> remove(Long[] ids);

    /**
     * 更新定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    Result<Void> edit(QuartzJobDTO job);

    /**
     * 定时任务状态修改
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    Result<Void> updateStatus(QuartzJobDTO job);

    /**
     * 立即执行一次定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return success/error
     */
    Result<Void> runAtNow(String jobName, String jobGroup);

    /**
     * 暂停定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    Result<Void> pause(String jobName, String jobGroup);

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    Result<Void> resume(String jobName, String jobGroup);

    /**
     * 根据任务名称判断定时任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    Result<Boolean> check(String jobName, String jobGroup);

    /**
     * 获取任务状态信息
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return 状态名称
     */
    Result<String> status(String jobName, String jobGroup);
}
```

直接在对应的业务controller调用即可，见`lejing-job`controller类：

```java

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Quartz定时任务调度Controller
 *
 * @author Weasley J
 * @version 1.0.1
 * @date 2021-08-29 16:29:58
 */
@Slf4j
@RestController
@RequestMapping("/schedule/job")
public class ScheduleJobController {

    /**
     * quartz任务调度核心方法
     */
    @Resource
    private QuartzCoreService quartzCoreService;

    /**
     * quartz定时任务调度业务Service
     */
    @Resource
    private QuartzJobService quartzJobService;

    /**
     * Java Bean转换
     */
    @Resource
    private ScheduleConvertor scheduleConvertor;

    /**
     * 获取时任务列表
     *
     * @param page 当前页
     * @param rows 每页大小
     * @return 定时任务分页列表
     */
    @GetMapping(value = "list")
    public Result<PageResult<QuartzJob>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows
    ) {
        PageResult<QuartzJob> pageResult = quartzJobService.queryPage(new PageDomain(page, rows, null, null), null);
        return Result.ok(pageResult);
    }

    /**
     * 新增cron定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody @Validated({InsertGroup.class}) QuartzJobDTO job) {
        return quartzJobService.save(job);
    }

    // ...
}  
```

- 自定义`JSR303` `cron`表达式校验注解`@Cron`介绍

```java
import cn.alphahub.common.valid.annotation.Cron;
import cn.alphahub.common.valid.group.EditGroup;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.common.valid.group.QueryGroup;
import cn.alphahub.mall.schedule.constant.ScheduleConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz定时任务调度 - DTO
 *
 * @author Weasley J
 * @date 2021-08-29 15:58:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QuartzJobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "任务ID不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 任务名称
     *
     * @required
     */
    @NotBlank(message = "任务名称不能为空", groups = {InsertGroup.class, EditGroup.class, QueryGroup.class})
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务执行类的全限定类名
     *
     * @required
     */
    @NotBlank(message = "任务执行类的全限定类名不能为空", groups = {InsertGroup.class, EditGroup.class})
    private String jobClass;

    /**
     * 任务参数（和JobDataMap对应，通过约定的key取出数据）
     */
    private String jobParams;

    /**
     * 任务描述（human-meaningful）
     */
    private String jobDescription;

    /**
     * cron执行表达式
     *
     * @required
     */
    @NotBlank(message = "cron执行表达式不能为空", groups = {InsertGroup.class, EditGroup.class})
    @Cron(message = "cron表达式不正确,请填写正确的cron表达式", groups = {InsertGroup.class, EditGroup.class})
    private String cronExpression;

    /**
     * 计划执行错误策略(失火策略: 0 默认; 1  立即执行;  2 执行一次; 3 放弃执行)
     */
    private Integer misfirePolicy;

    /**
     * 是否并发执行（1：允许；0：禁止）
     */
    private Integer isConcurrent;
    /**
     * 任务状态( 1 正常, 0 暂停，默认：1)
     */
    private Integer status;
    /**
     * 任务状态名称
     */
    private String statusName;
    /**
     * 创建时间
     *
     * @ignore
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新时间
     *
     * @ignore
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 备注信息
     */
    private String remark;

    public String getStatusName() {
        return StringUtils.isBlank(statusName) ? ScheduleConstant.JobStatusEnum.getName(status) : statusName;
    }
}
```

以上任务类`DTO`对象字段`private String cronExpression`被`@Cron`标注，这样此字段在便会和其他字段一样前端提交数据时（分组校验：保存、编辑情况）`Spring MVC`会通过`JSR303`校验规则检测提交数据的合法性。

## 4 创建定时任务示例

> **需求场景：**
>
> 我们需要创建一个订单发送邮件的任务.
>
> **需求分析：**
>
> 要完成以上需求我们需要：
>
> 1. 我们需要一个任务类触发任务执行.
> 2. 发送邮件我们可能需要邮件参数：收件人邮箱、邮件标题、邮件内容、附件等等.
> 3. 我们需要发件人的邮件配置，这里不做过多赘述，邮件支持模块我已经在`lejing-common/lejing-common-email-support`做好了，见此模块`readme.md`文件，直接将maven坐标引入`lejing-job`即可.

经过以上需求分析后，下面我们可以开工了！

### 4.1 创建任务类

```java
import cn.alphahub.mall.email.EmailTemplate;
import cn.alphahub.mall.email.annotation.Email;
import cn.alphahub.mall.schedule.constant.ScheduleConstant;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送邮件的定时任务
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-13
 */
@Slf4j
@Component
public class SendEmailJob extends QuartzJobBean {

    @Resource
    private EmailTemplate emailTemplate;

    @Override
    @SneakyThrows //简化一场代码快，如果非业务一场交给全局异常处理机制处理即可
    @Email(name = "EmailOffice365") //指定以outlook邮箱发送
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        String emailJsonString = dataMap.getString(ScheduleConstant.JOB_PARAM_KEY);
        log.warn("send email job:{}", JSONUtil.toJsonStr(emailJsonString));
        if (StringUtils.isNoneBlank(emailJsonString)) {
            EmailTemplate.MimeMessageDomain message = JSONUtil.toBean(emailJsonString, EmailTemplate.MimeMessageDomain.class);
            emailTemplate.send(message, null);
        }
    }
}
```

如果看过`org.springframework.scheduling.quartz.QuartzJobBean`的源码，细心的你应该会发现`org.springframework.scheduling.quartz.QuartzJobBean`实现了`org.quartz.Job`接口：

![image-20210914163841934](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210914163841934.png)

### 4.2 创建任务

- 准备job参数（发送邮件所需参数）

详见`api`接口: `/schedule/job/email/params`

![image-20210914171152184](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210914171152184.png)

![image-20210914171210341](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210914171210341.png)

**Response-example:**

```json
{
  "message": "success",
  "success": true,
  "timestamp": "2021-09-13 22:11:20",
  "code": 78,
  "data": {
    "to": "x34wd8",
    "sentDate": "2021-09-13 22:12:13",
    "subject": "tqdj3b",
    "text": "qe4r1l",
    "filepath": "7od593"
  }
}
```

- 访问接口提交创建job的数据

创建job接口：`/schedule/job/save`

**Body-parameters:**

![image-20210914172549897](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210914172549897.png)

**Request-body:**

```json
{
  "jobName": "SendEmail2021",
  "jobClass": "cn.alphahub.mall.schedule.job.module.email.SendEmailJob",
  "jobParams": "{\n\"to\": \"1432689025@qq.com\",\n\"subject\": \"邮件主题\",\n\"text\": \"邮件正文\",\n}",
  "jobDescription": "2021年9-12月每天晚上22:30:00发送job的任务",
  "cronExpression": "0 30 22 * 9,10,11,12 ? 2021",
  "misfirePolicy": 3,
  "isConcurrent": 1,
  "status": 1
}
```

发送请求：

**Curl-example:**

```shell
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://localhost:88/api/schedule/job/save --data '{
    "jobName": "SendEmail2021",
    "jobClass": "cn.alphahub.mall.schedule.job.module.email.SendEmailJob",
    "jobParams": "{\n\"to\": \"1432689025@qq.com\",\n\"subject\": \"邮件主题\",\n\"text\": \"邮件正文\",\n}",
    "jobDescription": "2021年9-12月每天晚上22:30:00发送job的任务",
    "cronExpression": "0 30 22 * 9,10,11,12 ? 2021",
    "misfirePolicy": 3,
    "isConcurrent": 1,
    "status": 1
}'
```
