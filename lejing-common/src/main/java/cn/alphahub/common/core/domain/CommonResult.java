package cn.alphahub.common.core.domain;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.top.abstraction.AbstractResult;
import cn.alphahub.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用数据封装结果返回
 *
 * @param <T> 返回对象
 * @author liuwenjing
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonResult<T> extends AbstractResult<T> implements Serializable {
    private static final long serialVersionUID = -7268040542410707954L;

    public CommonResult(Boolean success) {
        this.setSuccess(success);
    }

    public CommonResult(Boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public CommonResult(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public CommonResult(Boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }


    public static CommonResult<Void> ok() {
        return ok("操作成功");
    }

    public static <T> CommonResult<T> ok(String msg) {
        return baseCreate(HttpStatus.SUCCESS, msg, Boolean.TRUE, null);
    }

    public static <T> CommonResult<T> ok(T data) {
        return baseCreate(HttpStatus.SUCCESS, "操作成功", Boolean.TRUE, data);
    }

    public static <T> CommonResult<T> ok(String msg, T data) {
        return baseCreate(HttpStatus.SUCCESS, msg, Boolean.TRUE, data);
    }

    public static CommonResult<Void> fail() {
        return fail("操作失败");
    }

    public static CommonResult<Void> fail(String msg) {
        return fail(HttpStatus.ERROR, msg);
    }

    public static CommonResult<Void> fail(Integer code, String msg) {
        return baseCreate(code, msg, false, null);
    }

    private static <T> CommonResult<T> baseCreate(Integer code, String msg, Boolean success, T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setSuccess(success);
        result.setData(data);
        result.setTimestamp(DateUtils.dateTimeNow());
        return result;
    }

    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    @Override
    public T getData() {
        return super.getData();
    }
}
