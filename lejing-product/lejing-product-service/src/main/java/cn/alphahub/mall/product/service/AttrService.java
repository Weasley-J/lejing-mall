package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.vo.AttrGroupVO;
import cn.alphahub.mall.product.vo.AttrRespVO;
import cn.alphahub.mall.product.vo.AttrVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品属性Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
public interface AttrService extends IService<Attr> {

    /**
     * 新增商品属性
     *
     * @param attr 商品属性元数据
     * @return 成功返回true, 失败返回false
     */
    boolean saveAttr(AttrVO attr);

    /**
     * 查询商品属性分页列表
     *
     * @param pageDomain 分页数据
     * @param attr       分页对象
     * @return 商品属性分页数据
     */
    PageResult<Attr> queryPage(PageDomain pageDomain, Attr attr);

    /**
     * 查询分页
     *
     * @param pageDomain 分页数据实体
     * @param attr       商品属性
     * @param key        查询关键字
     * @param catelogId  分类id
     * @param attrType   属性类型
     * @return
     */
    PageResult<AttrRespVO> queryPage(PageDomain pageDomain, Attr attr, String key, Long catelogId, String attrType);

    /**
     * 修改
     *
     * @param attrVo
     * @return
     */
    boolean updateAttrById(AttrVO attrVo);

    /**
     * 获取商品属性详情
     *
     * @param attrId 商品属性主键id
     * @return 商品属性详细信息
     */
    AttrRespVO getAttrInfoById(Long attrId);

    /**
     * 获取当属性分组id的关联关系
     *
     * @param attrGroupId 属性分组id
     * @return 商品属性列表
     */
    List<Attr> getAttrRelations(Long attrGroupId);

    /**
     * 删除属性分组关联关系
     *
     * @param attrGroupVOList 属性分组集合
     */
    Integer removeRelations(List<AttrGroupVO> attrGroupVOList);

    /**
     * 获取当分组id的没有关联关系的属性
     *
     * @param attrGroupId 属性分组id
     * @param pageDomain  Pagehelper分页对象
     * @param key         搜索关键字
     * @return 属性分组分页数据
     */
    PageResult<Attr> getAttrNoRelations(PageDomain pageDomain, Long attrGroupId, String key);

    /**
     * 根据spu属性值id检索出是搜索属性的属性id集合
     *
     * @param attrValueIdList spu属性值id集合
     * @return 具备搜索属性的属性id集合
     */
    List<Long> querySearchAttrIds(List<Long> attrValueIdList);
}
