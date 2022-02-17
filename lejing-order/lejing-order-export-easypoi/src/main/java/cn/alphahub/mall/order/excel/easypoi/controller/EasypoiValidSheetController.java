package cn.alphahub.mall.order.excel.easypoi.controller;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.alphahub.common.core.domain.BaseResult;
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
    protected static final List<Person> PEOPLE = new ArrayList<>();

    static {
        PEOPLE.add(new Person(0L, "张三", 18, new BigDecimal("12"), 1));
        PEOPLE.add(new Person(1L, "", 19, new BigDecimal("13"), 1));
        PEOPLE.add(new Person(2L, "张五", 20, new BigDecimal("14"), 1));
        PEOPLE.add(new Person(3L, "张六", 21, new BigDecimal("15"), 1));
        PEOPLE.add(new Person(4L, "张七", 120, new BigDecimal("101"), 3));
    }


    /**
     * 下载excel文件
     */
    @ResponseBody
    @GetMapping(value = "/person/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String filename = "person-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        try {
            ExcelUtil.exportExcel(request, response, filename, new ExportParams("用户", "person"), Person.class, PEOPLE);
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
        ExcelUtil.previewHtml(response, entity, Person.class, PEOPLE);
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
