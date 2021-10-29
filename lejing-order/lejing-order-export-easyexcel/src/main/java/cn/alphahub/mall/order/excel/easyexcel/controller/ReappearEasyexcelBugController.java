package cn.alphahub.mall.order.excel.easyexcel.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.excel.easyexcel.listener.EasyExcelEventListener;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 重现easyexcel-bug
 *
 * @author lwj
 * @version 1.0
 * @date 2021-10-29 17:02
 */
@Slf4j
@RestController
@RequestMapping("/order/public/easyexcel")
public class ReappearEasyexcelBugController {

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
        String filename = "用户-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename.trim(), StandardCharsets.UTF_8));
            EasyExcel.write(response.getOutputStream(), Person.class).sheet("用户").doWrite(personList);
        } catch (IOException e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
    }

    /**
     * 文件上传
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
        log.info("上传订单excel文件.");
        EasyExcel.read(file.getInputStream(), Person.class, new EasyExcelEventListener<Person>() {
            @Override
            public void invoke(Person data, AnalysisContext context) {
                super.invoke(data, context);
                System.err.println(JSONUtil.toJsonStr(data));
            }
        }).sheet().doRead();
        return BaseResult.ok();
    }

    /**
     * Person
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true) // 链式调用是easyexcel底层反射创建对象时没处理好，倒是对象的字段为null
    public static class Person implements Serializable {
        private static final long serialVersionUID = 1L;

        @ExcelProperty(value = "id", index = 0)
        private Long id;

        @ExcelProperty(value = "名称", index = 1)
        private String name;

        @ExcelProperty(value = "年龄", index = 2)
        private Integer age;

        @ExcelProperty(value = "金额", index = 3)
        private BigDecimal amount;

        @ExcelProperty(value = "用户状态", index = 4)
        private Integer status;
    }
}
