package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.exception.NoStockException;
import cn.alphahub.common.mq.StockDetailTo;
import cn.alphahub.common.mq.StockLockedTo;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.feign.SkuInfoClient;
import cn.alphahub.mall.ware.mapper.WareSkuMapper;
import cn.alphahub.mall.ware.service.WareOrderTaskDetailService;
import cn.alphahub.mall.ware.service.WareOrderTaskService;
import cn.alphahub.mall.ware.service.WareSkuService;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static cn.alphahub.common.constant.MqConstant.STOCK_EVENT_EXCHANGE;
import static cn.alphahub.common.constant.MqConstant.STOCK_ROUTING_KEY_STOCK_LOCKED;

/**
 * 商品库存Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Slf4j
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {
    @Resource
    private WareSkuMapper wareSkuMapper;
    @Resource
    private SkuInfoClient skuInfoClient;
    @Resource
    private WareOrderTaskService wareOrderTaskService;
    @Resource
    private WareOrderTaskDetailService wareOrderTaskDetailService;
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 查询商品库存分页列表
     *
     * @param pageDomain 分页数据
     * @param wareSku    分页对象
     * @return 商品库存分页数据
     */
    @Override
    public PageResult<WareSku> queryPage(PageDomain pageDomain, WareSku wareSku) {
        QueryWrapper<WareSku> wrapper = new QueryWrapper<>(wareSku);
        PageResult<WareSku> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<WareSku> wareSkuList = this.list(wrapper);
        return pageResult.getPage(wareSkuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockStock(Long skuId, Long wareId, Long taskDetailId, Integer num) {
        // 1. 更新工作单状态
        WareOrderTaskDetail taskDetail = new WareOrderTaskDetail();
        taskDetail.setId(taskDetailId);
        taskDetail.setLockStatus(2);
        wareOrderTaskDetailService.updateById(taskDetail);
        // 2. 释放库存
        wareSkuMapper.unlockStock(skuId, wareId, num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockStock(Order order) {
        log.info("库存服务处理关闭订单:{}", cn.hutool.json.JSONUtil.toJsonStr(order));
        String orderSn = order.getOrderSn();
        if (StringUtils.isAllBlank(orderSn)) {
            log.warn("订单号不存在");
            return;
        }
        WareOrderTask one = wareOrderTaskService.getOne(new QueryWrapper<WareOrderTask>().lambda()
                .eq(WareOrderTask::getOrderSn, orderSn)
                .last(" LIMIT 1")
        );
        List<WareOrderTaskDetail> taskDetails = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetail>().lambda()
                .eq(WareOrderTaskDetail::getTaskId, one.getId())
                .eq(WareOrderTaskDetail::getLockStatus, 1)
        );
        for (WareOrderTaskDetail detail : taskDetails) {
            unlockStock(detail.getSkuId(), detail.getWareId(), detail.getId(), detail.getSkuNum());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWhetherCanUnlockStock(StockDetailTo stockDetail) {
        log.info("处理是否能解锁库存: {}", JSONUtil.toJsonStr(stockDetail));
        if (Objects.isNull(stockDetail)) {
            return;
        }
        if (Objects.equals(stockDetail.getLockStatus(), 1)) {
            this.unlockStock(stockDetail.getSkuId(), stockDetail.getWareId(), stockDetail.getId(), stockDetail.getSkuNum());
        }
    }

    /**
     * 更新库存信息
     *
     * @param skuId  产品skuId
     * @param wareId 库存id
     * @param skuNum 添加的库存量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addStock(Long skuId, Long wareId, Integer skuNum) {
        // 没有就新增，存下就更新
        QueryWrapper<WareSku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.lambda().eq(WareSku::getSkuId, skuId).eq(WareSku::getWareId, wareId);
        List<WareSku> wareSkus = wareSkuMapper.selectList(skuQueryWrapper);
        if (CollectionUtils.isNotEmpty(wareSkus)) {
            return wareSkuMapper.addStock(skuId, wareId, skuNum);
        } else {
            WareSku entity = WareSku.builder()
                    .skuId(skuId)
                    .wareId(wareId)
                    .stock(skuNum)
                    .stockLocked(0)
                    .build();
            try {
                Result<SkuInfo> result = skuInfoClient.info(skuId);
                if (result.getSuccess().equals(Boolean.TRUE)) {
                    var skuInfo = result.getData();
                    entity.setSkuName(skuInfo.getSkuName());
                }
            } catch (Exception e) {
                log.error("远程查询sku名称失败: {}", e.getMessage(), e);
            }
            return wareSkuMapper.insert(entity);
        }
    }

    @Override
    public List<WareSkuVO> getSkuHasStock(List<Long> skuIds) {
        return skuIds.stream().map(skuId -> {
            // 查当前sku可用库存总量: 每个仓库的总库存量 - 每个仓库锁定的总库存量
            Integer stock = baseMapper.getSkuStockBySkuId(skuId);
            return WareSkuVO.builder()
                    .skuId(skuId)
                    .stock(Objects.isNull(stock) ? 0 : stock)
                    .hasStock(ObjectUtils.isNotEmpty(stock) && stock > 0)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = NoStockException.class)
    public LockStockResultTo orderLockStock(WareSkuLockVo skuLockVo) {
        log.info("下单锁定库存:{}", cn.hutool.json.JSONUtil.toJsonStr(skuLockVo));
        if (ObjectUtils.isEmpty(skuLockVo)) {
            return null;
        }

        //保存库存工作详情
        Date now = new Date();
        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setOrderSn(skuLockVo.getOrderSn());
        wareOrderTask.setCreateTime(now);
        wareOrderTaskService.save(wareOrderTask);

        LockStockResultTo lockStockResult = new LockStockResultTo();
        List<LockStockResultTo.SkuLockStock> skuLockStocks = Lists.newArrayList();

        // 查询当前sku在那些仓库有库存
        List<SkuWareHasStockVo> hasStockVos = skuLockVo.getLocks().stream().map(orderItemVo -> {
            SkuWareHasStockVo hasStockVo = new SkuWareHasStockVo();
            List<Long> wareIds = wareSkuMapper.listWareIdWhichHasStock(orderItemVo.getSkuId());
            hasStockVo.setSkuId(orderItemVo.getSkuId());
            hasStockVo.setNum(orderItemVo.getCount());
            hasStockVo.setWareIds(wareIds);
            return hasStockVo;
        }).collect(Collectors.toList());

        AtomicReference<Boolean> allLocked = new AtomicReference<>(true);
        // 锁定库存
        for (SkuWareHasStockVo hasStockVo : hasStockVos) {
            AtomicReference<Boolean> skuLocked = new AtomicReference<>(false);
            Long skuId = hasStockVo.getSkuId();
            List<Long> wareIds = hasStockVo.getWareIds();
            if (ObjectUtils.isEmpty(wareIds)) {
                throw new NoStockException("商品id[" + skuId + "]库存不足!");
            }
            for (Long wareId : wareIds) {
                Integer count = wareSkuMapper.lockSkuStock(skuId, wareId, hasStockVo.getNum());
                // 锁定库存成功
                if (Objects.equals(count, 1)) {
                    skuLocked.set(true);
                    // 保存库存工作单
                    WareOrderTaskDetail orderTaskDetail = new WareOrderTaskDetail(null, skuId, null, hasStockVo.getNum(), wareOrderTask.getId(), wareId, 1);
                    Result<SkuInfo> result = skuInfoClient.info(skuId);
                    log.info("远程查询SKU信息：{}", cn.hutool.json.JSONUtil.toJsonStr(result));
                    if (result.getSuccess() && Objects.nonNull(result.getData())) {
                        orderTaskDetail.setSkuName(result.getData().getSkuName());
                    }
                    wareOrderTaskDetailService.save(orderTaskDetail);

                    // 准备MQ载荷数据
                    StockLockedTo stockLocked = new StockLockedTo();
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(orderTaskDetail, stockDetailTo);
                    stockLocked.setId(wareOrderTask.getId());
                    stockLocked.setDetailTo(stockDetailTo);

                    // 锁定库存成功就给MQ发消息
                    log.info("锁定库存成功就给MQ发消息:{}", cn.hutool.json.JSONUtil.toJsonStr(stockLocked));
                    amqpTemplate.convertAndSend(STOCK_EVENT_EXCHANGE, STOCK_ROUTING_KEY_STOCK_LOCKED, stockLocked, message -> {
                        message.getMessageProperties().setCorrelationId(IdUtil.fastSimpleUUID());
                        return message;
                    });
                    break;
                } else {
                    // 锁定库存失败
                    allLocked.set(false);
                    skuLocked.set(false);
                }
            }
            skuLockStocks.add(new LockStockResultTo.SkuLockStock()
                    .setSkuId(skuId)
                    .setNum(hasStockVo.num)
                    .setLocked(skuLocked.get())
            );
            // 当前商品所有仓库都没锁住
            if (skuLocked.get().equals(Boolean.FALSE)) {
                throw new NoStockException("商品id[" + skuId + "]没有库存了!");
            }
        }

        lockStockResult.setSkuLockStocks(skuLockStocks);
        lockStockResult.setIsAllSkuLocked(allLocked.get());

        // 能运行到这里锁定库存成功
        return lockStockResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(StockDetailTo detail) {
        log.info("真实扣减库存,修改库存工作单状态为已扣减:{}", cn.hutool.json.JSONUtil.toJsonStr(detail));
        var wareOrderTaskDetail = wareOrderTaskDetailService.getOne(new QueryWrapper<WareOrderTaskDetail>().lambda()
                .eq(WareOrderTaskDetail::getSkuId, detail.getSkuId())
                .eq(WareOrderTaskDetail::getTaskId, detail.getTaskId())
                .eq(WareOrderTaskDetail::getWareId, detail.getWareId())
                .eq(WareOrderTaskDetail::getLockStatus, detail.getLockStatus())
                .last(" LIMIT 1")
        );
        if (Objects.isNull(wareOrderTaskDetail)) {
            throw new BizException("库存工作单不存在!");
        }
        // 扣减库存
        this.baseMapper.reduceStock(wareOrderTaskDetail.getSkuId(), wareOrderTaskDetail.getWareId(), wareOrderTaskDetail.getSkuNum());

        // 修改状态
        var newWareOrderTaskDetail = new WareOrderTaskDetail();
        newWareOrderTaskDetail.setId(wareOrderTaskDetail.getId());
        newWareOrderTaskDetail.setLockStatus(3);
        wareOrderTaskDetailService.updateById(newWareOrderTaskDetail);
    }

    /**
     * 商品sku和仓库列表
     */
    @Data
    public static class SkuWareHasStockVo implements Serializable {
        /**
         * skuId
         */
        private Long skuId;
        /**
         * 锁定数量
         */
        private Integer num;
        /**
         * 仓库id列表
         */
        private List<Long> wareIds;
    }
}
