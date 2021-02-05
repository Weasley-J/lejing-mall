package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.BrandMapper;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.service.BrandService;

import java.util.List;

/**
 * 品牌Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    /**
     * 查询品牌分页列表
     *
     * @param pageDomain   分页数据
     * @param brand 分页对象
     * @return 品牌分页数据
     */
    @Override
    public PageResult<Brand> queryPage(PageDomain pageDomain, Brand brand) {
        pageDomain.startPage();
        QueryWrapper<Brand> wrapper = new QueryWrapper<>(brand);
        List<Brand> list = this.list(wrapper);
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        PageResult<Brand> pageResult = PageResult.<Brand>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}