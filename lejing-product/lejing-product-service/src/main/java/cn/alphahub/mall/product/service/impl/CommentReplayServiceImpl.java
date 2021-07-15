package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.CommentReplay;
import cn.alphahub.mall.product.mapper.CommentReplayMapper;
import cn.alphahub.mall.product.service.CommentReplayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品评价回复关系Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service
public class CommentReplayServiceImpl extends ServiceImpl<CommentReplayMapper, CommentReplay> implements CommentReplayService {

    /**
     * 查询商品评价回复关系分页列表
     *
     * @param pageDomain    分页数据
     * @param commentReplay 分页对象
     * @return 商品评价回复关系分页数据
     */
    @Override
    public PageResult<CommentReplay> queryPage(PageDomain pageDomain, CommentReplay commentReplay) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<CommentReplay> wrapper = new QueryWrapper<>(commentReplay);
        // 2. 创建一个分页对象
        PageResult<CommentReplay> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<CommentReplay> commentReplayList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(commentReplayList);
    }

}
