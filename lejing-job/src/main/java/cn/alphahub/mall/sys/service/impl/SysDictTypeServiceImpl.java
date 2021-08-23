package cn.alphahub.mall.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysDictType;
import cn.alphahub.mall.sys.mapper.SysDictTypeMapper;
import cn.alphahub.mall.sys.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典类型Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:08:07
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 查询字典类型分页列表
     *
     * @param pageDomain  分页数据
     * @param sysDictType 分页对象
     * @return 字典类型分页数据
     */
    @Override
    public PageResult<SysDictType> queryPage(PageDomain pageDomain, SysDictType sysDictType) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<>(sysDictType);
        // 2. 创建一个分页对象
        PageResult<SysDictType> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SysDictType> sysDictTypeList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(sysDictTypeList);
    }

}
