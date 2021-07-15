package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberCollectSpu;
import cn.alphahub.mall.member.mapper.MemberCollectSpuMapper;
import cn.alphahub.mall.member.service.MemberCollectSpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员收藏的商品Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberCollectSpuServiceImpl extends ServiceImpl<MemberCollectSpuMapper, MemberCollectSpu> implements MemberCollectSpuService {

    /**
     * 查询会员收藏的商品分页列表
     *
     * @param pageDomain       分页数据
     * @param memberCollectSpu 分页对象
     * @return 会员收藏的商品分页数据
     */
    @Override
    public PageResult<MemberCollectSpu> queryPage(PageDomain pageDomain, MemberCollectSpu memberCollectSpu) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberCollectSpu> wrapper = new QueryWrapper<>(memberCollectSpu);
        // 2. 创建一个分页对象
        PageResult<MemberCollectSpu> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberCollectSpu> memberCollectSpuList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberCollectSpuList);
    }

}
