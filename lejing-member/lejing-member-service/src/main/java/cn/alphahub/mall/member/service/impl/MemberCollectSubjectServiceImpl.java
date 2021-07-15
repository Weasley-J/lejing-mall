package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberCollectSubject;
import cn.alphahub.mall.member.mapper.MemberCollectSubjectMapper;
import cn.alphahub.mall.member.service.MemberCollectSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员收藏的专题活动Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberCollectSubjectServiceImpl extends ServiceImpl<MemberCollectSubjectMapper, MemberCollectSubject> implements MemberCollectSubjectService {

    /**
     * 查询会员收藏的专题活动分页列表
     *
     * @param pageDomain           分页数据
     * @param memberCollectSubject 分页对象
     * @return 会员收藏的专题活动分页数据
     */
    @Override
    public PageResult<MemberCollectSubject> queryPage(PageDomain pageDomain, MemberCollectSubject memberCollectSubject) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberCollectSubject> wrapper = new QueryWrapper<>(memberCollectSubject);
        // 2. 创建一个分页对象
        PageResult<MemberCollectSubject> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberCollectSubject> memberCollectSubjectList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberCollectSubjectList);
    }

}
