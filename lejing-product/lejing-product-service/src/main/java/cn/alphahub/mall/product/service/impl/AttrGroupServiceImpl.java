package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.AttrGroupMapper;
import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.service.AttrGroupService;

import java.util.List;

/**
 * 属性分组Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    /**
     * 查询属性分组分页列表
     *
     * @param pageDomain   分页数据
     * @param attrGroup 分页对象
     * @return 属性分组分页数据
     */
    @Override
    public PageResult<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup) {
        pageDomain.startPage();
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>(attrGroup);
        List<AttrGroup> list = this.list(wrapper);
        PageInfo<AttrGroup> pageInfo = new PageInfo<>(list);
        PageResult<AttrGroup> pageResult = PageResult.<AttrGroup>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}