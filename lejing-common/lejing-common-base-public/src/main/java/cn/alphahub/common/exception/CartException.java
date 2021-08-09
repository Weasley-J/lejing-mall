package cn.alphahub.common.exception;

/**
 * 购物车异常类
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
public class CartException extends RuntimeException {

    public CartException() {
        super();
    }

    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}
