package cn.alphahub.mall.order.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * mapstruct的java bean属性拷贝类
 * <p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/17
 */
@Mapper
public interface BeanUtil {

    /**
     * BeanUtil实例
     */
    BeanUtil INSTANCE = Mappers.getMapper(BeanUtil.class);

}
