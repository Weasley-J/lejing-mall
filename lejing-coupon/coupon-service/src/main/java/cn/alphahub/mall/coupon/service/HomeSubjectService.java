package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.coupon.domain.HomeSubject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
public interface HomeSubjectService extends IService<HomeSubject>, PageService<HomeSubject> {

}