package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 仓储采购表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface PurchaseDetailService extends IService<PurchaseDetail> {

    /**
     * 查询仓储采购表分页列表
     *
     * @param pageDomain     分页数据
     * @param purchaseDetail 分页对象
     * @return 仓储采购表分页数据
     */
    PageResult<PurchaseDetail> queryPage(PageDomain pageDomain, PurchaseDetail purchaseDetail);

    /**
     * 查询仓储采购表列表
     *
     * @param purchaseDetail 仓储采购表, 查询字段选择性传入, 默认为等值查询
     * @param key            检索关键字
     * @return 仓储采购表分页数据
     */
    PageResult<PurchaseDetail> queryPage(PageDomain pageDomain, PurchaseDetail purchaseDetail, String key);

    /**
     * 根据采购单id查询仓储采购列表
     *
     * @param purchaseId 采购单id
     * @return 仓储采购列表
     */
    List<PurchaseDetail> listDetailByPurchaseId(Long purchaseId);
}
