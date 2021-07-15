package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.constant.WareConstant;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.Purchase;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import cn.alphahub.mall.ware.mapper.PurchaseMapper;
import cn.alphahub.mall.ware.service.PurchaseDetailService;
import cn.alphahub.mall.ware.service.PurchaseService;
import cn.alphahub.mall.ware.service.WareSkuService;
import cn.alphahub.mall.ware.vo.MergeVo;
import cn.alphahub.mall.ware.vo.PurchaseDoneVo;
import cn.alphahub.mall.ware.vo.PurchaseItemVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Resource
    private PurchaseDetailService purchaseDetailService;
    @Resource
    private WareSkuService wareSkuService;

    /**
     * 查询采购信息分页列表
     *
     * @param pageDomain 分页数据
     * @param purchase   分页对象
     * @return 采购信息分页数据
     */
    @Override
    public PageResult<Purchase> queryPage(PageDomain pageDomain, Purchase purchase) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>(purchase);
        // 2. 创建一个分页对象
        PageResult<Purchase> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<Purchase> purchaseList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(purchaseList);
    }

    @Override
    public PageResult<Purchase> unReceiveList(PageDomain pageDomain, Purchase purchase) {
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>(purchase);
        wrapper.lambda().eq(Purchase::getStatus, 0).or().eq(Purchase::getStatus, 1);
        PageResult<Purchase> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<Purchase> purchaseList = this.list(wrapper);
        return pageResult.getPage(purchaseList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean merge(MergeVo mergeVo) {
        boolean flag1 = false, flag2 = false;
        Long purchaseId = mergeVo.getPurchaseId();
        if (ObjectUtils.isEmpty(purchaseId)) {
            //新建一个
            Purchase purchase = new Purchase();
            purchase.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchase.setCreateTime(new Date());
            flag1 = this.save(purchase);
            purchaseId = purchase.getId();
        }

        //TODO 确认采购单的状态是0或者1才可以合并

        List<Long> items = mergeVo.getItems();
        if (CollectionUtils.isNotEmpty(items)) {
            Long finalPurchaseId = purchaseId;
            List<PurchaseDetail> purchaseDetails = items.stream().map(id -> PurchaseDetail.builder()
                    .id(id)
                    .purchaseId(finalPurchaseId)
                    .status(WareConstant.PurchaseDemandStatusEnum.ASSIGNED.getCode())
                    .build()).collect(Collectors.toUnmodifiableList());
            flag2 = purchaseDetailService.updateBatchById(purchaseDetails);
        }

        Purchase purchase = new Purchase();
        purchase.setId(purchaseId);
        purchase.setUpdateTime(new Date());

        return flag1 || flag2 || this.updateById(purchase);
    }

    @Override
    public boolean received(List<Long> ids) {
        // 确认是当前采购单的状态: 新建、已分配
        List<Purchase> purchases = ids.stream().map(this::getById)
                .filter(purchase -> WareConstant.PurchaseStatusEnum.CREATED.getCode().equals(purchase.getStatus())
                        || WareConstant.PurchaseStatusEnum.ASSIGNED.getCode().equals(purchase.getStatus()))
                .peek(purchase -> {
                    purchase.setStatus(WareConstant.PurchaseStatusEnum.RECEIVED.getCode());
                    purchase.setUpdateTime(new Date());
                }).collect(Collectors.toList());
        // 改变采购单的状态
        boolean b1 = this.updateBatchById(purchases);
        // 改变采购项的状态
        purchases.forEach(purchase -> {
            List<PurchaseDetail> purchaseDetailList = this.purchaseDetailService.listDetailByPurchaseId(purchase.getId());
            List<PurchaseDetail> purchaseDetails = purchaseDetailList.stream().map(purchaseDetail -> {
                PurchaseDetail tmp = PurchaseDetail.builder().build();
                BeanUtils.copyProperties(purchaseDetail, tmp);
                // 更新采购状态
                tmp.setStatus(WareConstant.PurchaseDemandStatusEnum.PURCHASING.getCode());
                return tmp;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(purchaseDetails);
        });
        return b1;
    }

    @Override
    public boolean done(PurchaseDoneVo doneVo) {
        /* 采购单id */
        Long purchaseId = doneVo.getId();
        // 改变采购项的状态
        Boolean flag = Boolean.TRUE, success = Boolean.FALSE;
        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        List<PurchaseItemVo> items = doneVo.getItems();
        for (PurchaseItemVo item : items) {
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            if (WareConstant.PurchaseDemandStatusEnum.HAS_ERROR.getCode().equals(item.getStatus())) {
                flag = false;
                purchaseDetail.setStatus(WareConstant.PurchaseDemandStatusEnum.HAS_ERROR.getCode());
            } else {
                purchaseDetail.setStatus(WareConstant.PurchaseDemandStatusEnum.COMPLETED.getCode());
                // 将成功才的采购项进行入库
                PurchaseDetail detail = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum());
            }
            purchaseDetail.setId(item.getItemId());
            purchaseDetails.add(purchaseDetail);
        }
        purchaseDetailService.updateBatchById(purchaseDetails);
        // 改变采购单状态
        Purchase purchase = Purchase.builder().build();
        purchase.setId(purchaseId);
        purchase.setStatus(flag ? WareConstant.PurchaseStatusEnum.COMPLETED.getCode() : WareConstant.PurchaseDemandStatusEnum.HAS_ERROR.getCode());
        purchase.setUpdateTime(new Date());
        success = this.updateById(purchase);

        return success;
    }

}
