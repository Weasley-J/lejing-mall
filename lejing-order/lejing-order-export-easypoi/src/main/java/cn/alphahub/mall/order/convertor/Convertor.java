package cn.alphahub.mall.order.convertor;

import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.excel.easypoi.dto.OrderExcelDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.Date;

/**
 * mapstruct的java bean属性拷贝类
 * <p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/17
 */
@Mapper(componentModel = "spring", imports = {Arrays.class, Date.class})
public interface Convertor {
    /**
     * Order -> OrderExcelDTO
     *
     * @param order 订单
     * @return OrderExcelDTO
     */
    OrderExcelDTO copyToOrderExcelDTO(Order order);
}
