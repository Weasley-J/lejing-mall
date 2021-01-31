package cn.alphahub.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author liuwenjing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常消息
     */
    private String msg;
    /**
     * 错误码, 默认500
     */
    private Integer code = 500;

    public CustomizeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomizeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CustomizeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CustomizeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
