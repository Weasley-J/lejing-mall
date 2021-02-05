package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.product.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品三级分类Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
public interface CategoryService extends IService<Category>, PageService<Category> {

}