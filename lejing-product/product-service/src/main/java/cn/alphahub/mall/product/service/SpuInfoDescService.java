package cn.alphahub.mall.product.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.product.entity.SpuInfoDescEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:24:22
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

