package cn.alphahub.mall.reserve.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.sys.domain.SysDictType;
import cn.alphahub.mall.reserve.sys.mapper.SysDictTypeMapper;
import cn.alphahub.mall.reserve.sys.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典类型表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:19:03
 */
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 查询字典类型表分页列表
     *
     * @param pageDomain  分页数据
     * @param sysDictType 分页对象
     * @return 字典类型表分页数据
     */
    @Override
    public PageResult<SysDictType> queryPage(PageDomain pageDomain, SysDictType sysDictType) {
        pageDomain.startPage();
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<>(sysDictType);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>字典类型表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SysDictType> getPageResult(QueryWrapper<SysDictType> wrapper) {
        List<SysDictType> list = this.list(wrapper);
        PageInfo<SysDictType> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SysDictType>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
