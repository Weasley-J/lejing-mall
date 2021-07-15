package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.Purchase;
import cn.alphahub.mall.ware.vo.MergeVo;
import cn.alphahub.mall.ware.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 采购信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface PurchaseService extends IService<Purchase> {

    /**
     * 查询采购信息分页列表
     *
     * @param pageDomain 分页数据
     * @param purchase   分页对象
     * @return 采购信息分页数据
     */
    PageResult<Purchase> queryPage(PageDomain pageDomain, Purchase purchase);

    /**
     * 查询w未领取的采购单列表
     *
     * @param pageDomain 分页数据
     * @param purchase   采购信息, 查询字段选择性传入, 默认为等值查询
     * @return 采购信息分页数据
     */
    PageResult<Purchase> unReceiveList(PageDomain pageDomain, Purchase purchase);

    /**
     * 合并仓储采购表
     *
     * @param mergeVo 合并采购单元数据
     * @return 成功返回true, 失败返回false
     */
    boolean merge(MergeVo mergeVo);

    /**
     * 领取采购单
     *
     * @param ids 采购单id集合
     * @return 成功返回true, 失败返回false
     */
    boolean received(List<Long> ids);

    /**
     * 完成采购
     *
     * @param purchaseDoneVo 完成采购的参数元数据
     * @return 成功返回true, 失败返回false
     */
    boolean done(PurchaseDoneVo purchaseDoneVo);
}
