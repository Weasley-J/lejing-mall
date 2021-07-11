package cn.alphahub.mall.order.convertor;

import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.excel.easyexcel.dto.OrderItemExcelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
     * OrderItem ->  OrderItemExcelDTO
     *
     * @param orderItem 订单项数据
     * @return OrderItemExcelDTO
     */
    @Mappings(value = {
            @Mapping(target = "exportTime", expression = "java(new Date())"),
            @Mapping(target = "orderId", expression = "java(orderItem.getOrderId().toString())")
    })
    OrderItemExcelDTO copyToExcelDto(OrderItem orderItem);
}
