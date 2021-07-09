package cn.alphahub.mall.order.convertor;

import cn.alphahub.mall.order.domain.MqMessage;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.domain.PaymentInfo;
import cn.alphahub.mall.order.dto.response.MqMessageResp;
import cn.alphahub.mall.order.dto.vo.OrderItemVo;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import cn.alphahub.mall.order.excel.easyexcel.dto.OrderItemExcelDTO;
import cn.alphahub.mall.order.excel.easypoi.dto.OrderEasyPoiDTO;
import cn.alphahub.mall.order.excel.easypoi.dto.PaymentInfoEasyPoiDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
@Mapper(imports = {Arrays.class, Date.class})
public interface BeanUtil {

    /**
     * BeanUtil实例
     */
    BeanUtil INSTANCE = Mappers.getMapper(BeanUtil.class);

    /**
     * orderItem -> OrderItemVo
     *
     * @param orderItem 订单项信息
     * @return OrderItemVo
     */
    @Mappings(value = {
            @Mapping(target = "title", source = "skuName"),
            @Mapping(target = "image", source = "skuPic"),
            @Mapping(target = "skuAttrValues", expression = "java(Arrays.asList(orderItem.getSkuAttrsVals().split(\";\")))"),
            @Mapping(target = "price", source = "skuPrice"),
            @Mapping(target = "count", source = "skuQuantity"),
            @Mapping(target = "totalPrice", source = "realAmount")
    })
    OrderItemVo copy(OrderItem orderItem);

    /**
     * MqMessage -> MqMessageResp
     *
     * @param mqMessage MQ消息
     * @return MqMessageResp
     */
    @Mappings(value = {
            @Mapping(target = "status", source = "messageStatus")
    })
    MqMessageResp copy(MqMessage mqMessage);

    /**
     * order + orderItems -> OrderVo
     *
     * @param order 主订单
     * @return 订单数据
     */
    OrderVo copy(Order order);

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

    /**
     * Order -> OrderEasyPoiDTO
     *
     * @param order 订单
     * @return OrderEasyPoiDTO
     */
    OrderEasyPoiDTO copyToOrderEasyPoiDTO(Order order);

    /**
     * PaymentInfo -> PaymentInfoEasyPoiDTO
     *
     * @param paymentInfo 支付信息表
     * @return PaymentInfoEasyPoiDTO
     */
    PaymentInfoEasyPoiDTO copyToPaymentInfoEasyPoiDTO(PaymentInfo paymentInfo);
}
