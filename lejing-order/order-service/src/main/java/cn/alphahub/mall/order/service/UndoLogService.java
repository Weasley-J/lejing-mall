package cn.alphahub.mall.order.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.order.entity.UndoLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

