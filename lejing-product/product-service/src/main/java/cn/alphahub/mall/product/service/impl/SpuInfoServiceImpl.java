package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SpuInfoMapper;
import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.product.service.SpuInfoService;

import java.util.List;

/**
 * spu信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

    /**
     * 查询spu信息分页列表
     *
     * @param pageDomain   分页数据
     * @param spuInfo 分页对象
     * @return spu信息分页数据
     */
    @Override
    public PageResult<SpuInfo> queryPage(PageDomain pageDomain, SpuInfo spuInfo) {
        pageDomain.startPage();
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>(spuInfo);
        List<SpuInfo> list = this.list(wrapper);
        PageInfo<SpuInfo> pageInfo = new PageInfo<>(list);
        PageResult<SpuInfo> pageResult = PageResult.<SpuInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}