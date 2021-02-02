package cn.alphahub.mall.product.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:24:22
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

