package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubject;
import cn.alphahub.mall.coupon.mapper.HomeSubjectMapper;
import cn.alphahub.mall.coupon.service.HomeSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Service("homeSubjectService")
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectMapper, HomeSubject> implements HomeSubjectService {

    /**
     * 查询首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】分页列表
     *
     * @param pageDomain  分页数据
     * @param homeSubject 分页对象
     * @return 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】分页数据
     */
    @Override
    public PageResult<HomeSubject> queryPage(PageDomain pageDomain, HomeSubject homeSubject) {
        pageDomain.startPage();
        QueryWrapper<HomeSubject> wrapper = new QueryWrapper<>(homeSubject);
        List<HomeSubject> list = this.list(wrapper);
        PageInfo<HomeSubject> pageInfo = new PageInfo<>(list);
        PageResult<HomeSubject> pageResult = PageResult.<HomeSubject>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}
