package cn.alphahub.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.mapper.WareInfoMapper;
import cn.alphahub.mall.ware.domain.WareInfo;
import cn.alphahub.mall.ware.service.WareInfoService;

import java.util.List;

/**
 * 仓库信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements WareInfoService {

    /**
     * 查询仓库信息分页列表
     *
     * @param pageDomain   分页数据
     * @param wareInfo 分页对象
     * @return 仓库信息分页数据
     */
    @Override
    public PageResult<WareInfo> queryPage(PageDomain pageDomain, WareInfo wareInfo) {
        pageDomain.startPage();
        QueryWrapper<WareInfo> wrapper = new QueryWrapper<>(wareInfo);
        List<WareInfo> list = this.list(wrapper);
        PageInfo<WareInfo> pageInfo = new PageInfo<>(list);
        PageResult<WareInfo> pageResult = PageResult.<WareInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}