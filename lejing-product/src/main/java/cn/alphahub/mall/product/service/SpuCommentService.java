package cn.alphahub.mall.product.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.product.entity.SpuCommentEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品评价
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 04:31:16
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

