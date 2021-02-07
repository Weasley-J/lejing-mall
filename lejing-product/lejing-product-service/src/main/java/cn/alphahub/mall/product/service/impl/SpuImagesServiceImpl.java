package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SpuImagesMapper;
import cn.alphahub.mall.product.domain.SpuImages;
import cn.alphahub.mall.product.service.SpuImagesService;

import java.util.List;

/**
 * spu图片Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImages> implements SpuImagesService {

    /**
     * 查询spu图片分页列表
     *
     * @param pageDomain   分页数据
     * @param spuImages 分页对象
     * @return spu图片分页数据
     */
    @Override
    public PageResult<SpuImages> queryPage(PageDomain pageDomain, SpuImages spuImages) {
        pageDomain.startPage();
        QueryWrapper<SpuImages> wrapper = new QueryWrapper<>(spuImages);
        List<SpuImages> list = this.list(wrapper);
        PageInfo<SpuImages> pageInfo = new PageInfo<>(list);
        PageResult<SpuImages> pageResult = PageResult.<SpuImages>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}