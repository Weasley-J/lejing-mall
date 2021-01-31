package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.coupon.entity.HomeSubjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:27:55
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

