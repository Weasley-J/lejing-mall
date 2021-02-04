package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.WareOrderTaskDetailMapper;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.alphahub.mall.ware.service.WareOrderTaskDetailService;

import java.util.List;

/**
 * 库存工作单Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:22:49
 */
@Service("wareOrderTaskDetailService")
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetail> implements WareOrderTaskDetailService {

    /**
     * 查询库存工作单分页列表
     *
     * @param pageDomain   分页数据
     * @param wareOrderTaskDetail 分页对象
     * @return 库存工作单分页数据
     */
    @Override
    public PageResult<WareOrderTaskDetail> queryPage(PageDomain pageDomain, WareOrderTaskDetail wareOrderTaskDetail) {
        pageDomain.startPage();
        QueryWrapper<WareOrderTaskDetail> wrapper = new QueryWrapper<>(wareOrderTaskDetail);
        List<WareOrderTaskDetail> list = this.list(wrapper);
        PageInfo<WareOrderTaskDetail> pageInfo = new PageInfo<>(list);
        PageResult<WareOrderTaskDetail> pageResult = PageResult.<WareOrderTaskDetail>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}