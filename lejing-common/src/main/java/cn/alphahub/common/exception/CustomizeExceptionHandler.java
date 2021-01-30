package cn.alphahub.common.exception;

import cn.alphahub.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class CustomizeExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomizeException.class)
    public R customizeExceptionHandler(CustomizeException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R notFoundExceptionHandler(java.lang.Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R duplicateKeyExceptionHandler(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    /*
    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return R.error("没有权限，请联系管理员授权");
    }
    */

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error();
    }
}
