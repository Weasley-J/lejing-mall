package cn.alphahub.mall.ware.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.ware.entity.WareOrderTaskDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:14:08
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

