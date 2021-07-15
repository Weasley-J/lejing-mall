package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubject;
import cn.alphahub.mall.coupon.mapper.HomeSubjectMapper;
import cn.alphahub.mall.coupon.service.HomeSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
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
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<HomeSubject> wrapper = new QueryWrapper<>(homeSubject);
        // 2. 创建一个分页对象
        PageResult<HomeSubject> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<HomeSubject> homeSubjectList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(homeSubjectList);
    }

}
