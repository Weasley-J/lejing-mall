package cn.alphahub.mall.reserve.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.sys.domain.SysDictData;
import cn.alphahub.mall.reserve.sys.mapper.SysDictDataMapper;
import cn.alphahub.mall.reserve.sys.service.SysDictDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:19:03
 */
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    /**
     * 查询字典数据表分页列表
     *
     * @param pageDomain  分页数据
     * @param sysDictData 分页对象
     * @return 字典数据表分页数据
     */
    @Override
    public PageResult<SysDictData> queryPage(PageDomain pageDomain, SysDictData sysDictData) {
        pageDomain.startPage();
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<>(sysDictData);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>字典数据表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SysDictData> getPageResult(QueryWrapper<SysDictData> wrapper) {
        List<SysDictData> list = this.list(wrapper);
        PageInfo<SysDictData> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SysDictData>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
