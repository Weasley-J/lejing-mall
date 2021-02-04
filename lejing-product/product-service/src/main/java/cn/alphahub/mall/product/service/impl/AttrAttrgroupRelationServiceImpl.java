package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.AttrAttrgroupRelationMapper;
import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import cn.alphahub.mall.product.service.AttrAttrgroupRelationService;

import java.util.List;

/**
 * 属性&属性分组关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelation> implements AttrAttrgroupRelationService {

    /**
     * 查询属性&属性分组关联分页列表
     *
     * @param pageDomain   分页数据
     * @param attrAttrgroupRelation 分页对象
     * @return 属性&属性分组关联分页数据
     */
    @Override
    public PageResult<AttrAttrgroupRelation> queryPage(PageDomain pageDomain, AttrAttrgroupRelation attrAttrgroupRelation) {
        pageDomain.startPage();
        QueryWrapper<AttrAttrgroupRelation> wrapper = new QueryWrapper<>(attrAttrgroupRelation);
        List<AttrAttrgroupRelation> list = this.list(wrapper);
        PageInfo<AttrAttrgroupRelation> pageInfo = new PageInfo<>(list);
        PageResult<AttrAttrgroupRelation> pageResult = PageResult.<AttrAttrgroupRelation>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}