package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.UndoLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 撤销日志表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface UndoLogService extends IService<UndoLog> {

    /**
     * 查询撤销日志表分页列表
     *
     * @param pageDomain 分页数据
     * @param undoLog    分页对象
     * @return 撤销日志表分页数据
     */
    PageResult<UndoLog> queryPage(PageDomain pageDomain, UndoLog undoLog);

}
