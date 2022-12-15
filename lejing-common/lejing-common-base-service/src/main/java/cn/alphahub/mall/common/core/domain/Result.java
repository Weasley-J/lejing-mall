package cn.alphahub.mall.common.core.domain;

import cn.alphahub.mall.common.core.abstraction.AbstractResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 数据返回封装
 * <p>通用面向对象基础数据返回封装类
 *
 * @param <T> 返回数据对象
 * @author liuwenjing
 * @version 1.1.2
 * @date 2021年7月29日
 * @see AbstractResult<T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Result<T> extends AbstractResult<T> implements Serializable {
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final long serialVersionUID = -7804054241710086L;
    /**
     * 返回消息
     */
    private String message;

    /**
     * 是否成功
     */
    private Boolean success = false;

    /**
     * 响应时间
     */
    private String timestamp;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code    状态码
     * @param msg     返回内容
     * @param success 成功状态
     */
    public Result(Integer code, String msg, Boolean success) {
        this.setCode(code);
        this.setMessage(msg);
        this.setSuccess(success);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param success 成功状态
     */
    public Result(Boolean success) {
        this.setSuccess(success);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param success 成功状态
     * @param message 返回消息
     */
    public Result(Boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code    状态码
     * @param message 返回消息
     */
    public Result(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param success 成功提示
     * @param message 返回消息
     * @param data    数据对象
     */
    public Result(Boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Result(Integer code, String msg, T data) {
        this.setCode(code);
        this.setMessage(msg);
        this.setData(data);
    }

    /**
     * 类加载时初始化一个Result<T>对象
     *
     * @param code    状态码
     * @param msg     返回消息
     * @param success 成功标志
     * @param <T>     数据对象
     * @return Result封装好的数据对象
     */
    protected static <T> Result<T> init(Integer code, String msg, Boolean success, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setSuccess(success);
        result.setTimestamp(DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        result.setData(data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param <T> 数据对象
     * @return 成功消息
     */
    public static <T> Result<T> ok() {
        return of(null);
    }

    /**
     * 返回成功消息
     *
     * @param data 数据载荷
     * @param <T>  数据对象
     * @return 一个成功的Result对象
     */
    public static <T> Result<T> of(T data) {
        return of("操作成功", data);
    }

    /**
     * 携带数据返回成功消息
     *
     * @param <T>  数据对象
     * @param msg  返回消息
     * @param data 数据载体
     * @return 一个成功的Result对象
     */
    public static <T> Result<T> of(String msg, T data) {
        return init(200, msg, true, data);
    }

    /**
     * 返回成功消息
     *
     * @param <T>  数据对象
     * @param code 状态码
     * @param msg  返回内容
     * @return 成功消息
     */
    public static <T> Result<T> of(Integer code, String msg) {
        return init(code, msg, true, null);
    }

    /**
     * 返回成功消息
     *
     * @param <T> 数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success() {
        return success(200, "操作成功");
    }

    /**
     * 携带数据返回成功消息
     *
     * @param <T>  数据对象
     * @param data 封装返回的数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param <T>  数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success(Integer code, String msg) {
        return init(code, msg, true, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @param <T>  数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success(String msg, T data) {
        return init(200, msg, true, data);
    }

    /**
     * 返回失败消息
     *
     * @param <T> 数据对象
     * @return 失败消息
     */
    public static <T> Result<T> fail() {
        return fail(500, "操作失败");
    }

    /**
     * 返回失败消息
     *
     * @param <T> 数据对象
     * @param msg 返回消息
     * @return 失败消息
     */
    public static <T> Result<T> fail(String msg) {
        return fail(500, msg);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param code 响应状态码
     * @param msg  响应消息
     * @return 错误消息
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return init(code, msg, false, null);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param msg  响应消息
     * @param data 响应失败的消息体
     * @return 错误消息
     */
    public static <T> Result<T> fail(String msg, T data) {
        return init(400, msg, false, data);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param code 响应状态码
     * @param msg  响应提示消息
     * @param data 响应失败的消息体
     * @return 错误消息
     */
    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return init(code, msg, false, data);
    }

    /**
     * 返回错误消息
     *
     * @param <T> 数据对象
     * @return 错误消息
     */
    public static <T> Result<T> error() {
        return Result.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 提示消息
     * @param <T> 数据对象
     * @return 警告消息
     */
    public static <T> Result<T> error(String msg) {
        return Result.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param msg  提示消息
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> Result<T> error(String msg, T data) {
        return init(500, msg, false, data);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param code 状态码
     * @param msg  提示消息
     * @return 警告消息
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return init(code, msg, false, null);
    }

    /**
     * 返回错误消息
     *
     * @param <T>  数据对象
     * @param msg  提示消息
     * @param data 数据对象
     * @param code 状态码
     * @return 警告消息
     */
    public static <T> Result<T> error(Integer code, String msg, T data) {
        return init(code, msg, false, data);
    }

    /**
     * 封装数据并返回一个code为200的Result实例对象
     *
     * @param data 数据
     * @return Result实例
     */
    public Result<T> with(T data) {
        return this.with(200, data);
    }

    /**
     * 封装数据并返回一个Result实例并指定状态码
     *
     * @param data 数据
     * @param code 响应的状态码
     * @return Result实例
     */
    public Result<T> with(Integer code, T data) {
        return this.with(code, null, data);
    }

    /**
     * 封装数据并返回一个Result实例并指定状态码和提示消息
     *
     * @param data 数据
     * @param code 响应的状态码
     * @param msg  提示消息
     * @return Result实例
     */
    public Result<T> with(Integer code, String msg, T data) {
        this.setData(data);
        this.setCode(code);
        this.setMessage(msg);
        return this;
    }

}
