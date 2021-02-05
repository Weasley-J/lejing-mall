package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.HomeSubjectSpuMapper;
import cn.alphahub.mall.coupon.domain.HomeSubjectSpu;
import cn.alphahub.mall.coupon.service.HomeSubjectSpuService;

import java.util.List;

/**
 * 专题商品Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Service("homeSubjectSpuService")
public class HomeSubjectSpuServiceImpl extends ServiceImpl<HomeSubjectSpuMapper, HomeSubjectSpu> implements HomeSubjectSpuService {

    /**
     * 查询专题商品分页列表
     *
     * @param pageDomain   分页数据
     * @param homeSubjectSpu 分页对象
     * @return 专题商品分页数据
     */
    @Override
    public PageResult<HomeSubjectSpu> queryPage(PageDomain pageDomain, HomeSubjectSpu homeSubjectSpu) {
        pageDomain.startPage();
        QueryWrapper<HomeSubjectSpu> wrapper = new QueryWrapper<>(homeSubjectSpu);
        List<HomeSubjectSpu> list = this.list(wrapper);
        PageInfo<HomeSubjectSpu> pageInfo = new PageInfo<>(list);
        PageResult<HomeSubjectSpu> pageResult = PageResult.<HomeSubjectSpu>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}