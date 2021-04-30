package cn.alphahub.mall.order.exception;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.domain.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author liuwenjing
 */
@Slf4j
@RestControllerAdvice
public class BizExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public BaseResult<BizException> customizeExceptionHandler(BizException e) {
        return BaseResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult<BizException> notFoundExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResult<BizException> duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail("数据库中已存在该记录");
    }

    /*
    @ExceptionHandler(AuthorizationException.class)
    public BaseResult<BizException> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail("没有权限，请联系管理员授权");
    }*/

    @ExceptionHandler(Exception.class)
    public BaseResult<BizException> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(e.getLocalizedMessage());
    }
}
