package cn.alphahub.mall.schedule.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.sys.domain.SysDictType;
import cn.alphahub.mall.schedule.sys.mapper.SysDictTypeMapper;
import cn.alphahub.mall.schedule.sys.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典类型Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-28 22:03:32
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 查询字典类型分页列表
     *
     * @param page        分页参数
     * @param sysDictType 分页对象
     * @return 字典类型分页数据
     */
    @Override
    public PageResult<SysDictType> queryPage(PageDomain page, SysDictType sysDictType) {
        PageResult<SysDictType> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<SysDictType> sysDictTypeList = this.list(Wrappers.lambdaQuery(sysDictType));
        return pageResult.getPage(sysDictTypeList);
    }

}
