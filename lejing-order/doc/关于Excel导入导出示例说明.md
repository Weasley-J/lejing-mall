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



## 5 excel元数校验干掉千篇一律的if判断

见项目`lejing-order/lejing-order-export-easypoi`源码`cn.alphahub.mall.order.excel.easypoi.controller.EasypoiValidSheetController`

### 示例代码

```java
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.common.valid.annotation.DecimalRange;
import cn.alphahub.common.valid.annotation.ListValue;
import cn.alphahub.mall.order.excel.easypoi.util.ExcelUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Easypoi基于JSR303注解校验Excel表格内容
 *
 * @author lwj
 * @version 1.0
 * @date 2021-10-28 14:26
 */
@Slf4j
@Controller
@RequestMapping("/order/public/easypoi/valid/sheet")
public class EasypoiValidSheetController {

    /**
     * 用于模拟数据列表
     */
    private final List<Person> personList = new ArrayList<>() {{
        add(new Person(0L, "张三", 18, new BigDecimal("12"), 1));
        add(new Person(1L, "", 19, new BigDecimal("13"), 1));
        add(new Person(2L, "张五", 20, new BigDecimal("14"), 1));
        add(new Person(3L, "张六", 21, new BigDecimal("15"), 1));
        add(new Person(4L, "张七", 120, new BigDecimal("101"), 3));
    }};

    /**
     * 下载excel文件
     */
    @ResponseBody
    @GetMapping(value = "/person/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String filename = "person-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        try {
            ExcelUtil.exportExcel(request, response, filename, new ExportParams("用户", "person"), Person.class, personList);
        } catch (IOException e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * 文件上传（校验excel的元数据）
     *
     * @param file     前端提交的表单文件
     * @param request  http servlet request
     * @param response http servlet response
     * @return 返回校验失败的数据，包括行号和错误原因
     * @apiNote 校验用户上传excel文件类的表格的内容
     */
    @SneakyThrows
    @ResponseBody
    @PostMapping(value = "/person/upload")
    public BaseResult<List<Person>> upload(@RequestPart(name = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        log.warn("校验用户上传excel文件类的表格的内容");
        var params = new ImportParams();
        params.setNeedVerify(true);
        /* 标题有一行（如果要读取的文件没有就不用设置） */
        params.setTitleRows(1);
        params.setSheetNum(1);
        ExcelImportResult<Person> importResult = ExcelUtil.importExcelMore(file.getInputStream(), Person.class, params);
        List<Person> list = importResult.getList();
        for (Person person : list) {
            System.err.println(JSONUtil.toJsonStr(person));
        }
        return BaseResult.ok(importResult.getFailList());
    }

    /**
     * 预览excel内容
     *
     * @param response http servlet response
     * @page
     */
    @SneakyThrows
    @GetMapping("/preview/html")
    public void previewHtml(HttpServletResponse response) {
        ExportParams entity = new ExportParams();
        entity.setTitle("用户");
        ExcelUtil.previewHtml(response, entity, Person.class, personList);
    }

    /**
     * Person
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Person extends ExcelUtil.ExcelValidBaseEntity {
        private static final long serialVersionUID = 1L;

        @Excel(name = "id", needMerge = true, width = 20)
        @Min(value = 1L, message = "最小为1")
        private Long id;

        @Excel(name = "名称", orderNum = "1", needMerge = true, width = 20)
        @NotBlank(message = "不能为空")
        private String name;

        @Excel(name = "年龄", orderNum = "2", needMerge = true, width = 20)
        @Max(value = 100L, message = "不能超过100岁")
        @Min(value = 1, message = "不能低于1岁")
        private Integer age;

        @Excel(name = "金额", orderNum = "3", needMerge = true, width = 20)
        @DecimalRange(min = "0.00", max = "100.00", message = "必须在[0,100]之间")
        private BigDecimal amount;

        @Excel(name = "用户状态", orderNum = "4", needMerge = true, width = 20)
        @ListValue(value = {1, 0}, message = "只能提交{0,1}")
        private Integer status;
    }
}

```



### excel文件

![image-20211028181927783](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20211028181927783.png)



### 验证效果

![image-20211028192324475](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20211028192324475.png)



### 错误提示

我这里是把验证不通过的数据返回给前端，验证不通过的数据根据自己的业务体调整。

```json
{
    "message": "操作成功",
    "success": true,
    "timestamp": "2021-10-28 19:20:01",
    "code": 200,
    "data": [
        {
            "rowNum": 2,
            "errorMsg": "id最小为1",
            "id": 0,
            "name": "张三",
            "age": 18,
            "amount": 12,
            "status": 1
        },
        {
            "rowNum": 3,
            "errorMsg": "名称不能为空",
            "id": 1,
            "name": null,
            "age": 19,
            "amount": 13,
            "status": 1
        },
        {
            "rowNum": 6,
            "errorMsg": "年龄不能超过100岁,金额必须在[0,100]之间,用户状态只能提交{0,1}",
            "id": 4,
            "name": "张七",
            "age": 120,
            "amount": 101,
            "status": 3
        }
    ]
}
```

