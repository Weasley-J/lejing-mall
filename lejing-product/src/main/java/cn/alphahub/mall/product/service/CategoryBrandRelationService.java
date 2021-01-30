package cn.alphahub.mall.product.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 04:31:16
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

