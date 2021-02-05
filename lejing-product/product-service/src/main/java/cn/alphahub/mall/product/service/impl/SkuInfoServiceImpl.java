package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SkuInfoMapper;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.service.SkuInfoService;

import java.util.List;

/**
 * sku信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    /**
     * 查询sku信息分页列表
     *
     * @param pageDomain   分页数据
     * @param skuInfo 分页对象
     * @return sku信息分页数据
     */
    @Override
    public PageResult<SkuInfo> queryPage(PageDomain pageDomain, SkuInfo skuInfo) {
        pageDomain.startPage();
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>(skuInfo);
        List<SkuInfo> list = this.list(wrapper);
        PageInfo<SkuInfo> pageInfo = new PageInfo<>(list);
        PageResult<SkuInfo> pageResult = PageResult.<SkuInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}