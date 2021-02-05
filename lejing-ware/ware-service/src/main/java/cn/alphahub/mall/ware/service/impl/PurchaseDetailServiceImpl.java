package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.PurchaseDetailMapper;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import cn.alphahub.mall.ware.service.PurchaseDetailService;

import java.util.List;

/**
 * 仓储采购表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:37:25
 */
@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements PurchaseDetailService {

    /**
     * 查询仓储采购表分页列表
     *
     * @param pageDomain   分页数据
     * @param purchaseDetail 分页对象
     * @return 仓储采购表分页数据
     */
    @Override
    public PageResult<PurchaseDetail> queryPage(PageDomain pageDomain, PurchaseDetail purchaseDetail) {
        pageDomain.startPage();
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>(purchaseDetail);
        List<PurchaseDetail> list = this.list(wrapper);
        PageInfo<PurchaseDetail> pageInfo = new PageInfo<>(list);
        PageResult<PurchaseDetail> pageResult = PageResult.<PurchaseDetail>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}