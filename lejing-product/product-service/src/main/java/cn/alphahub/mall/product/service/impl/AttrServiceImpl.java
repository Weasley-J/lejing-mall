package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.AttrMapper;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.service.AttrService;

import java.util.List;

/**
 * 商品属性Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    /**
     * 查询商品属性分页列表
     *
     * @param pageDomain   分页数据
     * @param attr 分页对象
     * @return 商品属性分页数据
     */
    @Override
    public PageResult<Attr> queryPage(PageDomain pageDomain, Attr attr) {
        pageDomain.startPage();
        QueryWrapper<Attr> wrapper = new QueryWrapper<>(attr);
        List<Attr> list = this.list(wrapper);
        PageInfo<Attr> pageInfo = new PageInfo<>(list);
        PageResult<Attr> pageResult = PageResult.<Attr>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}