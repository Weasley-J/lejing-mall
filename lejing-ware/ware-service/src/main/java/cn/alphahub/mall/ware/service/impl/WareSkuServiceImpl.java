package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.WareSkuMapper;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.service.WareSkuService;

import java.util.List;

/**
 * 商品库存Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:22:49
 */
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

    /**
     * 查询商品库存分页列表
     *
     * @param pageDomain   分页数据
     * @param wareSku 分页对象
     * @return 商品库存分页数据
     */
    @Override
    public PageResult<WareSku> queryPage(PageDomain pageDomain, WareSku wareSku) {
        pageDomain.startPage();
        QueryWrapper<WareSku> wrapper = new QueryWrapper<>(wareSku);
        List<WareSku> list = this.list(wrapper);
        PageInfo<WareSku> pageInfo = new PageInfo<>(list);
        PageResult<WareSku> pageResult = PageResult.<WareSku>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}