package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.mapper.ProductAttrValueMapper;
import cn.alphahub.mall.product.service.ProductAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * spu属性值Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {

    /**
     * 查询spu属性值分页列表
     *
     * @param pageDomain       分页数据
     * @param productAttrValue 分页对象
     * @return spu属性值分页数据
     */
    @Override
    public PageResult<ProductAttrValue> queryPage(PageDomain pageDomain, ProductAttrValue productAttrValue) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>(productAttrValue);
        // 2. 创建一个分页对象
        PageResult<ProductAttrValue> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<ProductAttrValue> productAttrValueList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(productAttrValueList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProductAttrValues(List<ProductAttrValue> productAttrValues) {
        this.saveBatch(productAttrValues);
    }

    /**
     * 获取spu规格
     *
     * @param spuId spu Id
     * @return spu规格列表
     */
    @Override
    public List<ProductAttrValue> listSpuBySpuId(Long spuId) {
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ObjectUtils.isNotEmpty(spuId), ProductAttrValue::getSpuId, spuId);
        return this.list(wrapper);
    }

    /**
     * 根据spuId修改商品属性
     *
     * @param spuId      商品spuId
     * @param attrValues spu属性值列表
     * @return 成功返回true, 失败返回false
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSpuAttr(Long spuId, List<ProductAttrValue> attrValues) {
        //删除所有旧spu属性值
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>();
        boolean delete = this.remove(wrapper.lambda().eq(ObjectUtils.isNotEmpty(spuId), ProductAttrValue::getSpuId, spuId));
        List<ProductAttrValue> valueList = attrValues.stream()
                .peek(productAttrValue -> productAttrValue.setSpuId(spuId))
                .collect(Collectors.toList());
        //保存新spu属性
        boolean saveBatch = this.saveBatch(valueList);
        return delete || saveBatch;
    }
}
