package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import cn.alphahub.mall.product.mapper.AttrAttrgroupRelationMapper;
import cn.alphahub.mall.product.service.AttrAttrgroupRelationService;
import cn.alphahub.mall.product.vo.AttrGroupRelationVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 属性&属性分组关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelation> implements AttrAttrgroupRelationService {

    /**
     * 查询属性&属性分组关联分页列表
     *
     * @param pageDomain            分页数据
     * @param attrAttrgroupRelation 分页对象
     * @return 属性&属性分组关联分页数据
     */
    @Override
    public PageResult<AttrAttrgroupRelation> queryPage(PageDomain pageDomain, AttrAttrgroupRelation attrAttrgroupRelation) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<AttrAttrgroupRelation> wrapper = new QueryWrapper<>(attrAttrgroupRelation);
        // 2. 创建一个分页对象
        PageResult<AttrAttrgroupRelation> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<AttrAttrgroupRelation> attrAttrgroupRelationList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(attrAttrgroupRelationList);
    }


    @Override
    public Boolean addBatchRelations(List<AttrGroupRelationVO> relationVos) {
        relationVos = relationVos.stream().map(attrGroupRelationVO -> {
            AttrGroupRelationVO relationVO = new AttrGroupRelationVO();
            QueryWrapper<AttrAttrgroupRelation> wrapper = new QueryWrapper<>();
            AttrAttrgroupRelation relation = this.getOne(
                    wrapper.lambda().eq(AttrAttrgroupRelation::getAttrId, attrGroupRelationVO.getAttrId()).eq(AttrAttrgroupRelation::getAttrGroupId, attrGroupRelationVO.getAttrGroupId())
            );
            if (ObjectUtils.isNull(relation)) {
                BeanUtils.copyProperties(attrGroupRelationVO, relationVO);
            }
            return relationVO;
        }).collect(Collectors.toList());

        List<AttrAttrgroupRelation> attrgroupRelations = new ArrayList<>();
        for (AttrGroupRelationVO attrGroupRelationVO : relationVos) {
            AttrAttrgroupRelation attrgroupRelation = new AttrAttrgroupRelation();
            if (ObjectUtils.isNotNull(attrGroupRelationVO)) {
                BeanUtils.copyProperties(attrGroupRelationVO, attrgroupRelation);
                attrgroupRelations.add(attrgroupRelation);
            }
        }
        return this.saveBatch(attrgroupRelations);
    }
}
