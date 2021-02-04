package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.PurchaseMapper;
import cn.alphahub.mall.ware.domain.Purchase;
import cn.alphahub.mall.ware.service.PurchaseService;

import java.util.List;

/**
 * 采购信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:22:49
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    /**
     * 查询采购信息分页列表
     *
     * @param pageDomain   分页数据
     * @param purchase 分页对象
     * @return 采购信息分页数据
     */
    @Override
    public PageResult<Purchase> queryPage(PageDomain pageDomain, Purchase purchase) {
        pageDomain.startPage();
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>(purchase);
        List<Purchase> list = this.list(wrapper);
        PageInfo<Purchase> pageInfo = new PageInfo<>(list);
        PageResult<Purchase> pageResult = PageResult.<Purchase>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}