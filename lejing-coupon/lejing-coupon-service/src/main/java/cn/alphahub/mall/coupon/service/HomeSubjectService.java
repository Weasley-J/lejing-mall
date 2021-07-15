package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface HomeSubjectService extends IService<HomeSubject> {

    /**
     * 查询首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】分页列表
     *
     * @param pageDomain  分页数据
     * @param homeSubject 分页对象
     * @return 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】分页数据
     */
    PageResult<HomeSubject> queryPage(PageDomain pageDomain, HomeSubject homeSubject);

}
