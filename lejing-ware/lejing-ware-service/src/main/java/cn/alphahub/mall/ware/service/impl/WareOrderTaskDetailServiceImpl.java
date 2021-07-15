package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.alphahub.mall.ware.mapper.WareOrderTaskDetailMapper;
import cn.alphahub.mall.ware.service.WareOrderTaskDetailService;
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
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetail> implements WareOrderTaskDetailService {

    /**
     * 查询库存工作单分页列表
     *
     * @param pageDomain          分页数据
     * @param wareOrderTaskDetail 分页对象
     * @return 库存工作单分页数据
     */
    @Override
    public PageResult<WareOrderTaskDetail> queryPage(PageDomain pageDomain, WareOrderTaskDetail wareOrderTaskDetail) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<WareOrderTaskDetail> wrapper = new QueryWrapper<>(wareOrderTaskDetail);
        // 2. 创建一个分页对象
        PageResult<WareOrderTaskDetail> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<WareOrderTaskDetail> wareOrderTaskDetailList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(wareOrderTaskDetailList);
    }

}
