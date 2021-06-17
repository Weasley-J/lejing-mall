package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import cn.alphahub.mall.product.mapper.SpuInfoDescMapper;
import cn.alphahub.mall.product.service.SpuInfoDescService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * spu信息介绍Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Service
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescMapper, SpuInfoDesc> implements SpuInfoDescService {

    /**
     * 查询spu信息介绍分页列表
     *
     * @param pageDomain  分页数据
     * @param spuInfoDesc 分页对象
     * @return spu信息介绍分页数据
     */
    @Override
    public PageResult<SpuInfoDesc> queryPage(PageDomain pageDomain, SpuInfoDesc spuInfoDesc) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SpuInfoDesc> wrapper = new QueryWrapper<>(spuInfoDesc);
        // 2. 创建一个分页对象
        PageResult<SpuInfoDesc> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SpuInfoDesc> spuInfoDescList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(spuInfoDescList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfoDesc(SpuInfoDesc spuInfoDesc) {
        baseMapper.insert(spuInfoDesc);
    }
}
