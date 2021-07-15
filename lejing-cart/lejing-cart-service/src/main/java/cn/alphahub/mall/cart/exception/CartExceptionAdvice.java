package cn.alphahub.mall.cart.exception;

import cn.alphahub.common.core.domain.BaseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>运行时异常处理类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
@ControllerAdvice
public class CartExceptionAdvice {

    /**
     * 全局统一异常处理
     *
     * @param exception 异常
     * @return 错误提示
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public BaseResult<Object> handleUnknownException(RuntimeException exception) {
        return BaseResult.error(exception.getMessage());
    }

    /**
     * 购物车中找不到商品
     *
     * @param exception 异常
     * @return 错误提示
     */
    @ExceptionHandler(CartException.class)
    public BaseResult<Object> handleUserCustomizeException(CartException exception) {
        return BaseResult.error("购物车无此商品");
    }
}
