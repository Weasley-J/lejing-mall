package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubjectSpu;
import cn.alphahub.mall.coupon.mapper.HomeSubjectSpuMapper;
import cn.alphahub.mall.coupon.service.HomeSubjectSpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专题商品Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class HomeSubjectSpuServiceImpl extends ServiceImpl<HomeSubjectSpuMapper, HomeSubjectSpu> implements HomeSubjectSpuService {

    /**
     * 查询专题商品分页列表
     *
     * @param pageDomain     分页数据
     * @param homeSubjectSpu 分页对象
     * @return 专题商品分页数据
     */
    @Override
    public PageResult<HomeSubjectSpu> queryPage(PageDomain pageDomain, HomeSubjectSpu homeSubjectSpu) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<HomeSubjectSpu> wrapper = new QueryWrapper<>(homeSubjectSpu);
        // 2. 创建一个分页对象
        PageResult<HomeSubjectSpu> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<HomeSubjectSpu> homeSubjectSpuList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(homeSubjectSpuList);
    }

}
