package cn.alphahub.mall.order.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 库存不足异常
 *
 * @author lwj
 * @version 1.0
 * @date 2021/05/19
 */
public class NoStockException extends RuntimeException {

    @Getter
    @Setter
    private Long skuId;

    public NoStockException(Long skuId) {
        super("商品id：" + skuId + "库存不足！");
    }

    public NoStockException(String msg) {
        super(msg);
    }
}
