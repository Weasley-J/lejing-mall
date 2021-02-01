package cn.alphahub.mall.ware.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:14:08
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

