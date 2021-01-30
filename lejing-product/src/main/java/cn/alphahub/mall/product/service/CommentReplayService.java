package cn.alphahub.mall.product.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 04:31:16
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

