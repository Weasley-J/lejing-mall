package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import cn.alphahub.mall.product.vo.AttrGroupRelationVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 属性&属性分组关联Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelation> {

    /**
     * 新增属性分组关联关系
     *
     * @param relationVos 属性分组集合
     */
    Boolean addBatchRelations(List<AttrGroupRelationVO> relationVos);

    /**
     * 查询属性&属性分组关联分页列表
     *
     * @param pageDomain            分页数据
     * @param attrAttrgroupRelation 分页对象
     * @return 属性&属性分组关联分页数据
     */
    PageResult<AttrAttrgroupRelation> queryPage(PageDomain pageDomain, AttrAttrgroupRelation attrAttrgroupRelation);

}
