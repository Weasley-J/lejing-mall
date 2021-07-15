package cn.alphahub.mall.ware.convertor;

import cn.alphahub.common.mq.StockDetailTo;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
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

    /**
     * WareOrderTaskDetail -> StockDetailTo
     *
     * @param taskDetail 库存工作单
     * @return StockDetailTo
     */
    StockDetailTo copy(WareOrderTaskDetail taskDetail);
}
