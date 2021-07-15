package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import cn.alphahub.mall.ware.mapper.WareOrderTaskMapper;
import cn.alphahub.mall.ware.service.WareOrderTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存工作单Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Service
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTask> implements WareOrderTaskService {

    /**
     * 查询库存工作单分页列表
     *
     * @param pageDomain    分页数据
     * @param wareOrderTask 分页对象
     * @return 库存工作单分页数据
     */
    @Override
    public PageResult<WareOrderTask> queryPage(PageDomain pageDomain, WareOrderTask wareOrderTask) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<WareOrderTask> wrapper = new QueryWrapper<>(wareOrderTask);
        // 2. 创建一个分页对象
        PageResult<WareOrderTask> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<WareOrderTask> wareOrderTaskList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(wareOrderTaskList);
    }

}
