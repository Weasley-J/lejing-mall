package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.LockStockResultTo;
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
     * @param skuId  sku id
     * @param wareId 仓库id
     * @param num    解锁数量
     */
    void unlockStock(Long skuId, Long wareId, Integer num);

    /**
     * 更新库存信息
     *
     * @param skuId  产品skuId
     * @param wareId 库存id
     * @param skuNum 添加的库存量
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
}
