package cn.alphahub.mall.product.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 属性分组
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:24:22
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

