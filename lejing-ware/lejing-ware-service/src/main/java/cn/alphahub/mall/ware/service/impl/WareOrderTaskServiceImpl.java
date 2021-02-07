package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.WareOrderTaskMapper;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import cn.alphahub.mall.ware.service.WareOrderTaskService;

import java.util.List;

/**
 * 库存工作单Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTask> implements WareOrderTaskService {

    /**
     * 查询库存工作单分页列表
     *
     * @param pageDomain   分页数据
     * @param wareOrderTask 分页对象
     * @return 库存工作单分页数据
     */
    @Override
    public PageResult<WareOrderTask> queryPage(PageDomain pageDomain, WareOrderTask wareOrderTask) {
        pageDomain.startPage();
        QueryWrapper<WareOrderTask> wrapper = new QueryWrapper<>(wareOrderTask);
        List<WareOrderTask> list = this.list(wrapper);
        PageInfo<WareOrderTask> pageInfo = new PageInfo<>(list);
        PageResult<WareOrderTask> pageResult = PageResult.<WareOrderTask>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}