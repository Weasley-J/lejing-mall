package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.mapper.AttrGroupMapper;
import cn.alphahub.mall.product.service.AttrGroupService;
import cn.alphahub.mall.product.service.AttrService;
import cn.alphahub.mall.product.vo.AttrGroupWithAttrsVO;
import cn.alphahub.mall.product.vo.SpuItemAttrGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 属性分组Service业务层处理
 *
 * @author Weasley J
 * @date 2021-02-24 15:36:31
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    @Resource
    private AttrService attrService;

    /**
     * 查询属性分组分页列表
     *
     * @param pageDomain 分页数据
     * @param attrGroup  分页对象
     * @return 属性分组分页数据
     */
    @Override
    public PageResult<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>(attrGroup);
        // 2. 创建一个分页对象
        PageResult<AttrGroup> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<AttrGroup> attrGroupList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(attrGroupList);
    }


    /**
     * 根据catelogId查询属性分组列表
     *
     * @param pageDomain 分页数据
     * @param attrGroup  分页对象
     * @param key        查询关键字
     * @return 属性分组分页
     */
    @Override
    public PageResult<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup, String key) {
        pageDomain.startPage();
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>(attrGroup);
        //select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(qw -> qw.lambda().eq(AttrGroup::getAttrGroupId, key).or().like(AttrGroup::getAttrGroupName, key));
        }
        return getAttrGroupPageResult(wrapper);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1、查出当前分类下的所有属性分组
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AttrGroup::getCatelogId, catelogId);
        List<AttrGroup> attrGroups = this.list(wrapper);
        //2、查出每个属性分组的所有属性
        return attrGroups.stream().map(attrGroup -> {
            AttrGroupWithAttrsVO attrsVO = new AttrGroupWithAttrsVO();
            BeanUtils.copyProperties(attrGroup, attrsVO);
            List<Attr> attrRelations = attrService.getAttrRelations(attrGroup.getAttrGroupId());
            attrsVO.setAttrs(attrRelations);
            return attrsVO;
        }).collect(Collectors.toList());
    }

    private PageResult<AttrGroup> getAttrGroupPageResult(QueryWrapper<AttrGroup> wrapper) {
        List<AttrGroup> list = this.list(wrapper);
        PageInfo<AttrGroup> pageInfo = new PageInfo<>(list);
        PageResult<AttrGroup> pageResult = new PageResult<>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

    /**
     * 根据商品spuId获取商品sku的对应属性组
     *
     * @param catalogId 三级分类id
     * @param spuId     商品spuId
     * @return 商品sku属性列表
     */
    @Override
    public List<SpuItemAttrGroupVO> listBySpuIdAndCatalogId(Long catalogId, Long spuId) {
        return this.baseMapper.listBySpuIdAndCatalogId(catalogId, spuId);
    }
}
