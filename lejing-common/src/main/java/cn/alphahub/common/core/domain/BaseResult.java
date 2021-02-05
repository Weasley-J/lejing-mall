package cn.alphahub.common.core.domain;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.core.top.abstraction.AbstractResult;
import cn.alphahub.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 通用Controller数据返回封装
 *
 * @param <T> 返回对象
 * @author liuwenjing
 * @date 2021年2月2日22:58:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResult<T> extends AbstractResult<T> implements Serializable {
    private static final long serialVersionUID = -7268040542410710086L;

    /**
     * 初始化一个新创建的 T 对象，使其表示一个空消息
     */
    public BaseResult() {
    }

    /**
     * 初始化一个新创建的 T 对象
     *
     * @param code    状态码
     * @param msg     返回内容
     * @param success 成功状态
     */
    public BaseResult(int code, String msg, boolean success) {
        this.setCode(code);
        this.setMessage(msg);
        this.setSuccess(success);
    }

    /**
     * 初始化一个新创建的 T 对象
     *
     * @param success 成功状态
     */
    public BaseResult(Boolean success) {
        this.setSuccess(success);
    }

    /**
     * 初始化一个新创建的 T 对象
     *
     * @param success 成功状态
     * @param message 返回消息
     */
    public BaseResult(Boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    /**
     * 初始化一个新创建的 T 对象
     *
     * @param code    状态码
     * @param message 返回消息
     */
    public BaseResult(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * 初始化一个新创建的 T 对象
     *
     * @param success 成功提示
     * @param message 返回消息
     * @param data    数据对象
     */
    public BaseResult(Boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public BaseResult(int code, String msg, T data) {
        this.setCode(code);
        this.setMessage(msg);
        if (ObjectUtils.isNotEmpty(data)) {
            this.setData(data);
        }
    }

    /**
     * 类加载时初始化一个BaseResult<T>对象
     *
     * @param code    状态码
     * @param msg     返回消息
     * @param success 成功标志
     * @param <T>     数据对象
     * @return BaseResult封装好的数据对象
     */
    private static <T> BaseResult<T> preCreate(Integer code, String msg, Boolean success, T data) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setSuccess(success);
        result.setTimestamp(DateUtils.getTime());
        result.setData(data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param <T> 数据对象
     * @return 成功消息
     */
    public static <T> BaseResult<T> ok() {
        return ok("操作成功");
    }

    /**
     * 携带数据返回成功消息
     *
     * @param data 返回消息
     * @param <T>  数据对象
     * @return 数据对象
     */
    public static <T> BaseResult<T> ok(T data) {
        return preCreate(HttpStatus.SUCCESS, "操作成功", Boolean.TRUE, data);
    }

    /**
     * 携带数据返回成功消息
     *
     * @param msg  返回消息
     * @param data 数据载体
     * @param <T>  数据对象
     * @return 封装的数据
     */
    public static <T> BaseResult<T> ok(String msg, T data) {
        return preCreate(HttpStatus.SUCCESS, msg, Boolean.TRUE, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回消息
     * @param <T> 数据对象
     * @return 数据对象
     */
    public static <T> BaseResult<T> ok(String msg) {
        return preCreate(HttpStatus.SUCCESS, msg, Boolean.TRUE, null);
    }

    /**
     * 返回失败消息
     *
     * @param <T> 数据对象
     * @return 失败消息
     */
    public static <T> BaseResult<T> fail() {
        return fail(HttpStatus.ERROR, "操作失败");
    }

    /**
     * 返回失败消息
     *
     * @param <T> 数据对象
     * @param msg 返回消息
     * @return 失败消息
     */
    public static <T> BaseResult<T> fail(String msg) {
        return fail(HttpStatus.ERROR, msg);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param code 响应状态码
     * @param msg  响应消息
     * @return 错误消息
     */
    public static <T> BaseResult<T> fail(Integer code, String msg) {
        return preCreate(code, msg, false, null);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public BaseResult<T> success() {
        return new BaseResult<T>().success("操作成功");
    }

    /**
     * 携带数据返回成功消息
     *
     * @param data 封装返回的数据对象
     * @return 成功消息
     */
    public BaseResult<T> success(T data) {
        return new BaseResult<T>().success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public BaseResult<T> success(String msg) {
        return new BaseResult<T>().success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public BaseResult<T> success(String msg, T data) {
        return new BaseResult<>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public BaseResult<T> error() {
        return new BaseResult<T>().error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public BaseResult<T> error(String msg) {
        return new BaseResult<T>().error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public BaseResult<T> error(String msg, T data) {
        return new BaseResult<>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public BaseResult<T> error(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }

    /**
     * 返回分页列表
     *
     * @param items      分页集合
     * @param totalCount 总记录数
     * @param totalPage  总页数
     * @return 分页数据集合
     */
    public BaseResult<PageResult<T>> queryPage(List<T> items, Long totalCount, Long totalPage) {
        PageResult<T> pageResult = PageResult.<T>builder()
                .totalCount(totalCount)
                .totalPage(totalPage)
                .items(items)
                .build();
        if (Objects.nonNull(pageResult)) {
            return new BaseResult<PageResult<T>>().success("查询成功", pageResult);
        }
        return new BaseResult<PageResult<T>>().error("查询失败");
    }

    public BaseResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    @Override
    public T getData() {
        return super.getData();
    }
}
