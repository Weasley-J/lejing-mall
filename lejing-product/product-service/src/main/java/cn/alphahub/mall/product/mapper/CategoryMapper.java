package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
	
}