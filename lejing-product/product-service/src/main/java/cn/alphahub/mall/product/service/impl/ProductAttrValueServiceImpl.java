package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.Query;
import cn.alphahub.mall.product.dao.ProductAttrValueDao;
import cn.alphahub.mall.product.entity.ProductAttrValueEntity;
import cn.alphahub.mall.product.service.ProductAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}