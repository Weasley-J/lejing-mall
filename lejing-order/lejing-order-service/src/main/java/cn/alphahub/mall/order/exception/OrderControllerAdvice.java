package cn.alphahub.mall.order.exception;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enums.BusinessCodeEnum;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.exception.NoStockException;
import cn.hutool.json.JSONUtil;
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
@RestControllerAdvice(basePackages = {"cn.alphahub.mall.order.controller"})
public class OrderControllerAdvice {

    /**
     * 数据校验异常
     *
     * @param e 异常对象
     * @return
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
                BusinessCodeEnum.VALID_EXCEPTION.getCode(),
                BusinessCodeEnum.VALID_EXCEPTION.getMessage(),
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
                BusinessCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                BusinessCodeEnum.UNKNOWN_EXCEPTION.getMessage(),
                "Caused by:" + throwable.getLocalizedMessage()
        );
    }

    @ExceptionHandler(value = BizException.class)
    public BaseResult<Object> handleBizException(BizException bizException) {
        log.error("错误信息: {}", JSONUtil.toJsonStr(bizException));
        return BaseResult.error(
                bizException.getCode(),
                bizException.getMsg(),
                "Caused by:" + bizException.getLocalizedMessage()
        );
    }

    @ExceptionHandler(value = NoStockException.class)
    public BaseResult<Object> handleBizException(NoStockException noStockException) {
        return BaseResult.error(noStockException.getMessage());
    }
}
