package cn.alphahub.common.core.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.exception.BizException;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.constraints.NotNull;
import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层通用数据处理
 *
 * @author liuwenjing
 */
@Slf4j
public class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * feign远程调用通过原实体类class对象获取BaseResult分装data体里面的原型数据
     *
     * @param result       BaseResult分装结果集
     * @param requiredType BaseResult分装的data体里面实体类的class对象
     * @param <T>          类对象实体
     * @return 转换后的对象实体数据
     * @since 2021年2月8日23:09:20
     */
    @SuppressWarnings("unchecked")
    protected <T> T doConvertType(@NotNull BaseResult<?> result, @NotNull Class<T> requiredType) {
        Object data = result.getData();
        if (ObjectUtils.allNull(data)) {
            throw new BizException("传入数据为空！");
        }
        if (requiredType.isInstance(data)) {
            return (T) data;
        } else {
            throw new BizException("类型换换异常，请检查转换的类型与所需类型是否一致！");
        }
    }

    /**
     * 响应返回结果
     *
     * @param rows 受影响行数(insert|update|delete)
     * @return 操作结果
     */
    protected BaseResult<Integer> toAffectedRows(int rows) {
        return rows > 0 ? BaseResult.ok(rows) : BaseResult.fail();
    }

    /**
     * 获取 insert|update|delete 的操作结果
     *
     * @param flag mybatis-plus insert|update|delete的操作返回值
     * @return 操作提示
     */
    protected BaseResult<Boolean> toOperationResult(Boolean flag) {
        return flag ? BaseResult.ok() : BaseResult.fail();
    }

    /**
     * 获取实体对象数据并返回给前端
     *
     * @param entity 需要返回的数据实体对象
     * @return 传入对象不为null返回封装的实体数据
     */
    protected BaseResult<?> toResponseResult(Object entity) {
        if (ObjectUtils.isNotEmpty(entity)) {
            return BaseResult.ok("查询成功", entity);
        }
        return BaseResult.fail(404, "查询失败,查找信息为空.");
    }
}
