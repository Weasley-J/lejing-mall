package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuImages;
import cn.alphahub.mall.product.mapper.SpuImagesMapper;
import cn.alphahub.mall.product.service.SpuImagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * spu图片Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Slf4j
@Service
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImages> implements SpuImagesService {

    /**
     * 查询spu图片分页列表
     *
     * @param pageDomain 分页数据
     * @param spuImages  分页对象
     * @return spu图片分页数据
     */
    @Override
    public PageResult<SpuImages> queryPage(PageDomain pageDomain, SpuImages spuImages) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SpuImages> wrapper = new QueryWrapper<>(spuImages);
        // 2. 创建一个分页对象
        PageResult<SpuImages> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SpuImages> spuImagesList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(spuImagesList);
    }


    /**
     * 保存图片
     *
     * @param spuInfoId
     * @param images
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(Long spuInfoId, List<String> images) {
        if (CollectionUtils.isEmpty(images)) {
            log.warn("图片集合为空images：{}", images);
        } else {
            List<SpuImages> spuImages = images.stream().map(imageUrl ->
                    SpuImages.builder()
                            .id(spuInfoId)
                            .imgUrl(imageUrl)
                            .build()).collect(Collectors.toList());
            this.saveBatch(spuImages);
        }
    }
}
