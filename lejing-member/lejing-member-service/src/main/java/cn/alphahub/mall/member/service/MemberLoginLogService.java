package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员登录记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberLoginLogService extends IService<MemberLoginLog> {

    /**
     * 查询会员登录记录分页列表
     *
     * @param pageDomain     分页数据
     * @param memberLoginLog 分页对象
     * @return 会员登录记录分页数据
     */
    PageResult<MemberLoginLog> queryPage(PageDomain pageDomain, MemberLoginLog memberLoginLog);

}
