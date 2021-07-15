package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import cn.alphahub.mall.ware.mapper.PurchaseDetailMapper;
import cn.alphahub.mall.ware.service.PurchaseDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 仓储采购表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements PurchaseDetailService {

    /**
     * 查询仓储采购表分页列表
     *
     * @param pageDomain     分页数据
     * @param purchaseDetail 分页对象
     * @return 仓储采购表分页数据
     */
    @Override
    public PageResult<PurchaseDetail> queryPage(PageDomain pageDomain, PurchaseDetail purchaseDetail) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>(purchaseDetail);
        // 2. 创建一个分页对象
        PageResult<PurchaseDetail> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<PurchaseDetail> purchaseDetailList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(purchaseDetailList);
    }

    /**
     * 查询仓储采购表列表
     *
     * @param purchaseDetail 仓储采购表, 查询字段选择性传入, 默认为等值查询
     * @param key            检索关键字
     * @return 仓储采购表分页数据
     */
    @Override
    public PageResult<PurchaseDetail> queryPage(PageDomain pageDomain, PurchaseDetail purchaseDetail, String key) {
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>(purchaseDetail);
        wrapper.lambda().and(StringUtils.isNotBlank(key),
                w -> w.eq(PurchaseDetail::getPurchaseId, key)
                        .or().eq(PurchaseDetail::getSkuId, key)
        );
        PageResult<PurchaseDetail> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<PurchaseDetail> purchaseDetailList = this.list(wrapper);
        return pageResult.getPage(purchaseDetailList);
    }

    @Override
    public List<PurchaseDetail> listDetailByPurchaseId(Long purchaseId) {
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>();
        return list(wrapper.lambda().eq(Objects.nonNull(purchaseId), PurchaseDetail::getPurchaseId, purchaseId));
    }

}
