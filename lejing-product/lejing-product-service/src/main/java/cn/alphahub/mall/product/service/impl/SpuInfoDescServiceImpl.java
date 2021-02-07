package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SpuInfoDescMapper;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import cn.alphahub.mall.product.service.SpuInfoDescService;

import java.util.List;

/**
 * spu信息介绍Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescMapper, SpuInfoDesc> implements SpuInfoDescService {

    /**
     * 查询spu信息介绍分页列表
     *
     * @param pageDomain   分页数据
     * @param spuInfoDesc 分页对象
     * @return spu信息介绍分页数据
     */
    @Override
    public PageResult<SpuInfoDesc> queryPage(PageDomain pageDomain, SpuInfoDesc spuInfoDesc) {
        pageDomain.startPage();
        QueryWrapper<SpuInfoDesc> wrapper = new QueryWrapper<>(spuInfoDesc);
        List<SpuInfoDesc> list = this.list(wrapper);
        PageInfo<SpuInfoDesc> pageInfo = new PageInfo<>(list);
        PageResult<SpuInfoDesc> pageResult = PageResult.<SpuInfoDesc>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}