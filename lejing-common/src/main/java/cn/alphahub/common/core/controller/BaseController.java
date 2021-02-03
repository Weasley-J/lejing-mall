package cn.alphahub.common.core.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Objects;

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
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 响应返回结果
     *
     * @param rows 受影响行数(insert|update|delete)
     * @return 操作结果
     */
    protected BaseResult<Integer> toAffectedRows(int rows) {
        BaseResult<Integer> result = new BaseResult<>();
        return rows > 0 ? result.success(rows) : result.error();
    }


    /**
     * 获取 insert|update|delete 的操作结果
     *
     * @param flag mybatis-plus insert|update|delete的操作返回值
     * @return 操作提示
     */
    protected BaseResult<Boolean> toOperationResult(Boolean flag) {
        BaseResult<Boolean> result = new BaseResult<>();
        return flag ? result.success(Boolean.TRUE) : result.error();
    }

    /**
     * 获取实体对象数据并返回给前端
     *
     * @param entity 需要返回的数据实体对象
     * @return 传入对象不为null返回封装的实体数据
     */
    protected BaseResult<?> toResponseResult(Object entity) {
        BaseResult<?> result = new BaseResult<>();
        return Objects.nonNull(entity) ? result.success("查询成功", entity) : result.error("查询失败");
    }

    /**
     * 获取分页结果分页结果集
     *
     * @param pageResult 页查询结果对象
     * @return 分页结果分页结果集
     */
    protected BaseResult<?> toPageableResult(PageResult<?> pageResult) {
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return new BaseResult<PageResult<?>>().success("查询成功", pageResult);
        }
        return new BaseResult<PageResult<?>>().error("查询失败");
    }
}
