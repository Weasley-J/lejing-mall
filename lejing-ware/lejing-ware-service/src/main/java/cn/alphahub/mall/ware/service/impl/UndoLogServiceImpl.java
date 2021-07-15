package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.UndoLog;
import cn.alphahub.mall.ware.mapper.UndoLogMapper;
import cn.alphahub.mall.ware.service.UndoLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 撤销日志表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Service
public class UndoLogServiceImpl extends ServiceImpl<UndoLogMapper, UndoLog> implements UndoLogService {

    /**
     * 查询撤销日志表分页列表
     *
     * @param pageDomain 分页数据
     * @param undoLog    分页对象
     * @return 撤销日志表分页数据
     */
    @Override
    public PageResult<UndoLog> queryPage(PageDomain pageDomain, UndoLog undoLog) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<UndoLog> wrapper = new QueryWrapper<>(undoLog);
        // 2. 创建一个分页对象
        PageResult<UndoLog> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<UndoLog> undoLogList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(undoLogList);
    }

}
