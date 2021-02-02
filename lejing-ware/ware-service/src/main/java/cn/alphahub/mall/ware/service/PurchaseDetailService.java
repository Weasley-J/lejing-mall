package cn.alphahub.mall.ware.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.ware.entity.PurchaseDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:14:08
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

