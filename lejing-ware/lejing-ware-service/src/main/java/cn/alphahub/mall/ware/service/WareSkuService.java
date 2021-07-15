package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.mq.StockDetailTo;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品库存Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareSkuService extends IService<WareSku> {

    /**
     * 查询商品库存分页列表
     *
     * @param pageDomain 分页数据
     * @param wareSku    分页对象
     * @return 商品库存分页数据
     */
    PageResult<WareSku> queryPage(PageDomain pageDomain, WareSku wareSku);

    /**
     * 解锁库存(减少的库存加回去)
     *
     * @param skuId        sku id
     * @param wareId       仓库id
     * @param taskDetailId 库存工作单id
     * @param num          解锁数量
     */
    void unlockStock(Long skuId, Long wareId, Long taskDetailId, Integer num);

    /**
     * <b>库存服务处理关闭订单事件</b>
     * <p>
     * 防止订单库存服务卡顿,导致订单消息一直不能修改, 库存消息优先到期,
     * 查询的订单状态一直新建状态, 订单状态卡顿, 库存得不到解锁.
     * </p>
     *
     * @param order 订单
     */
    void unlockStock(Order order);

    /**
     * 处理是否能解锁库存
     *
     * @param stockDetail 库存工作单的锁定数据，锁定状态： 1 已锁定，2 已解锁， 3 已扣减
     */
    void handleWhetherCanUnlockStock(StockDetailTo stockDetail);

    /**
     * 更新库存信息
     *
     * @param skuId  产品skuId
     * @param wareId 库存id
     * @param skuNum 添加的库存量
     * @return rows
     */
    Integer addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 查看是否有库存
     *
     * @param skuIds sku id 集合
     * @return 商品库存列表
     */
    List<WareSkuVO> getSkuHasStock(List<Long> skuIds);

    /**
     * 下单锁定库存
     * <p><b>锁库存场景：</b>
     * <ul>
     *     <li>下单成功</li>
     *     <li>超时未付</li>
     *     <li>被用户手动取消</li>
     * </ul>
     * <ul>
     *     <li>下单成功，存库锁定成功，接下来的业务代码逻辑调用失败，导致之前订单数据回滚，之前锁定的库存就要被释放掉（还原回去）</li>
     * </ul>
     * </p>
     *
     * @param skuLockVo 锁定库存
     * @return 库存锁定结果
     */
    LockStockResultTo orderLockStock(WareSkuLockVo skuLockVo);

    /**
     * 真实的减库存, 锁定多少， 库存量减多少，修改库存工作单的状态为：3 已扣减
     * <ul>
     *     <b>主要做两件事</b>
     *     <li>减少sku的库存量</li>
     *     <li>修改库存工作单的状态为: 3 已扣减</li>
     * </ul>
     *
     * @param detail MQ库存数据对象
     */
    void reduceStock(StockDetailTo detail);
}
