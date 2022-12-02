package cn.alphahub.mall.product.domain;

import cn.alphahub.common.util.IdSerializer;
import cn.alphahub.common.valid.annotation.ListValue;
import cn.alphahub.common.valid.group.Edit;
import cn.alphahub.common.valid.group.EditStatus;
import cn.alphahub.common.valid.group.Insert;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_brand")
public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @Null(message = "新增不能指定id", groups = {Insert.class})
    @NotNull(message = "修改必须指定id", groups = {Edit.class})
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long brandId;

    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {Insert.class, Edit.class})
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * 品牌logo地址
     */
    @NotBlank(groups = {Insert.class})
    @URL(message = "logo必须是一个合法的URL链接", groups = {Insert.class, Edit.class})
    private String logo;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(groups = {Insert.class, EditStatus.class})
    @ListValue(value = {0, 1}, groups = {Insert.class, EditStatus.class})
    private Integer showStatus;

    /**
     * 检索首字母
     */
    @NotBlank(groups = {Insert.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个a-z或A-Z的字母", groups = {Insert.class, Edit.class})
    private String firstLetter;

    /**
     * 排序
     */
    @NotNull(groups = {Insert.class})
    @Min(value = 0, message = "排序值必须≥0", groups = {Insert.class, Edit.class})
    private Integer sort;

}
