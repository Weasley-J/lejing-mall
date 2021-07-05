package cn.alphahub.common.exception;

import cn.alphahub.common.core.domain.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

/**
 * 全局异常处理
 *
 * @author liuwenjing
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义异常
     *
     * @param e 业务异常
     * @return 错误信息
     */
    @ExceptionHandler(BizException.class)
    public BaseResult<BizException> customizeExceptionHandler(BizException e) {
        return BaseResult.fail(e.getCode(), e.getMessage());
    }

    /**
     * 找不到处理程序异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult<BizException> notFoundExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(404, "路径不存在，请检查路径是否正确");
    }

    /**
     * 重复键异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResult<BizException> duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail("数据库中已存在该记录");
    }

    /**
     * 异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<BizException> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(e.getLocalizedMessage());
    }
}
