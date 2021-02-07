package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.ware.domain.UndoLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 撤销日志表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
public interface UndoLogService extends IService<UndoLog>, PageService<UndoLog> {

}