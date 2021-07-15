package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员等级Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberLevelService extends IService<MemberLevel> {

    /**
     * 查询会员等级分页列表
     *
     * @param pageDomain  分页数据
     * @param memberLevel 分页对象
     * @return 会员等级分页数据
     */
    PageResult<MemberLevel> queryPage(PageDomain pageDomain, MemberLevel memberLevel);

}
