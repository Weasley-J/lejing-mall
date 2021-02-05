package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.product.domain.UndoLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 撤销日志表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:32
 */
public interface UndoLogService extends IService<UndoLog>, PageService<UndoLog> {

}