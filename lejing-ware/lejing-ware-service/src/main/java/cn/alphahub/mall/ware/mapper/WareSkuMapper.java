package cn.alphahub.mall.ware.mapper;

import cn.alphahub.mall.ware.domain.WareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品库存
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSku> {

    /**
     * 更新库存信息
     *
     * @param skuId  产品skuId
     * @param wareId 库存id
     * @param skuNum 添加的库存量
     */
    Integer addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    /**
     * 查当前sku可用库存总量: 每个仓库的总库存量 - 每个仓库锁定的总库存量
     *
     * @param skuId skuId
     * @return 可用库存总量
     */
    Integer getSkuStockBySkuId(@Param("skuId") Long skuId);

    /**
     * 查询当前sku在那个仓库有库存
     *
     * @param skuId 商品skuId
     * @return 仓库id列表
     */
    List<Long> listWareIdWhichHasStock(@Param("skuId") Long skuId);

    /**
     * 锁定库存
     *
     * @param skuId  商品skuId
     * @param wareId 仓库id
     * @param num    锁定数量
     * @return 受影响行数
     */
    Integer lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);

    /**
     * 解锁库存(减少的库存加回去)
     *
     * @param skuId  sku id
     * @param wareId 仓库id
     * @param num    解锁数量
     * @return update rows
     */
    @Update({"update wms_ware_sku set stock_locked = stock_locked - #{num} where sku_id = #{skuId} and ware_id = #{wareId} "})
    Integer unlockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);

    /**
     * 扣减库存，释放锁定库存量
     *
     * @param skuId  sku id
     * @param wareId 仓库id
     * @param skuNum 扣减的商品数量
     * @return update rows
     */
    Integer reduceStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
