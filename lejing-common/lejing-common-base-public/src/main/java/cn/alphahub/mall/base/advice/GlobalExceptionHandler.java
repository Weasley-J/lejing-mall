package cn.alphahub.mall.base.advice;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enums.BizCodeEnum;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.exception.CartException;
import cn.alphahub.common.exception.NoStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局异常处理（集中处理全局异常处理）
 *
 * @author liuwenjing
 */
@Slf4j
@RestControllerAdvice(basePackages = {"cn.alphahub.mall"})
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
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
    public BaseResult<Object> notFoundExceptionHandler(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(404, "路径不存在，请检查路径是否正确: " + e.getMessage());
    }

    /**
     * 重复键异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResult<Object> duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail("数据库中已存在该记录: " + e.getMessage());
    }

    /**
     * 数据校验异常
     *
     * @param e 异常对象
     * @return 数据校验异常具体错误信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult<Object> handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, Map<String, Object>> errorMap = new LinkedHashMap<>();
        Map<String, Object> globalErrorMap = new LinkedHashMap<>();
        Map<String, Object> fieldErrorMap = new LinkedHashMap<>();
        result.getGlobalErrors().forEach(objectError -> {
            globalErrorMap.putIfAbsent(objectError.getObjectName(), e.getTarget());
            globalErrorMap.putIfAbsent("errorMsg", objectError.getDefaultMessage());
        });
        result.getFieldErrors().forEach(fieldError -> fieldErrorMap.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));
        errorMap.putIfAbsent("globalError", globalErrorMap);
        errorMap.putIfAbsent("fieldError", fieldErrorMap);
        log.error("数据校验异常：{}，异常类型：{}", e.getMessage(), e.getClass());
        return BaseResult.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMessage(), errorMap);
    }

    /**
     * 购物车中找不到商品
     *
     * @param ce 购物车异常
     * @return 错误提示
     */
    @ExceptionHandler(CartException.class)
    public BaseResult<Object> handleUserCustomizeException(CartException ce) {
        log.error(ce.getMessage(), ce);
        return BaseResult.error("购物车无此商品");
    }

    /**
     * 库存不足异常
     *
     * @param noStockException 库存不足异常
     * @return 错误提示
     */
    @ExceptionHandler(value = NoStockException.class)
    public BaseResult<Object> handleBizException(NoStockException noStockException) {
        log.error("异常信息：{}", noStockException.getMessage(), noStockException);
        return BaseResult.error(noStockException.getMessage());
    }

    /**
     * Exception异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<Object> exceptionHandler(Exception e) {
        log.error("异常信息：{}", e.getLocalizedMessage(), e);
        return BaseResult.fail(e.getMessage());
    }

    /**
     * RuntimeException异常
     *
     * @param re 异常
     * @return 错误提示
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResult<Object> handleUnknownException(RuntimeException re) {
        log.error("运行期间抛出的异常：{}", re.getMessage(), re);
        return BaseResult.error(re.getMessage());
    }

    /**
     * Throwable系统未知异常
     *
     * @param throwable 异常对象
     * @return 异常提示
     */
    @ExceptionHandler(value = Throwable.class)
    public BaseResult<Object> handleException(Throwable throwable) {
        log.error("错误信息: ", throwable);
        return BaseResult.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMessage(), "Caused by:" + throwable.getLocalizedMessage());
    }
}
