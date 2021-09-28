package cn.alphahub.mall.sms.exception;

/**
 * sms param empty exception
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-28 18:30
 */
public class SmsParamEmptyException extends RuntimeException {

    public SmsParamEmptyException() {
        super();
    }

    public SmsParamEmptyException(String message) {
        super(message);
    }

    public SmsParamEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
