package cn.alphahub.mall.valid.exception;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enums.BizCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 集中处理全局异常处理
 *
 * @author liuwenjing
 */
@Slf4j
@RestControllerAdvice(basePackages = {"cn.alphahub.mall.valid.controller"})
public class ControllerAdvice {

    /**
     * 数据校验异常
     *
     * @param e 异常对象
     * @return 异常提示
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult<Object> handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, Object> errorMap = new LinkedHashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            String errorField = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errorMap.putIfAbsent(errorField, errorMessage);
        });
        log.error("数据校验异常：{}，异常类型：{}", e.getMessage(), e.getClass());
        return BaseResult.error(
                BizCodeEnum.VALID_EXCEPTION.getCode(),
                BizCodeEnum.VALID_EXCEPTION.getMessage(),
                errorMap
        );
    }

    /**
     * 系统未知异常
     *
     * @param throwable 异常对象
     * @return 异常提示
     */
    @ExceptionHandler(value = Throwable.class)
    public BaseResult<Object> handleException(Throwable throwable) {
        log.error("错误信息: ", throwable);
        return BaseResult.error(
                BizCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                BizCodeEnum.UNKNOWN_EXCEPTION.getMessage(),
                "Caused by:" + throwable.getLocalizedMessage());
    }

}
