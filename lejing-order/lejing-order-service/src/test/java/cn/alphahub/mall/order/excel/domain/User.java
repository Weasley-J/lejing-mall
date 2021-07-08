package cn.alphahub.mall.order.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "学生ID", index = 0)
    private int id;

    @ExcelProperty(value = "学姓名", index = 1)
    private String name;
}
