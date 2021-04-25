package cn.alphahub.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 自定义异常
 *
 * @author liuwenjing
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常消息
     */
    private String msg;

    /**
     * 错误码, 默认500
     */
    private Integer code = 500;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
