package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 品牌Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface BrandService extends IService<Brand> {

    /**
     * 根据关键字查询品牌分页列表
     *
     * @param pageDomain 分页数据
     * @param brand      分页对象
     * @param searchKey  查询关键字
     * @return 品牌分页数据
     */
    PageResult<Brand> queryPage(PageDomain pageDomain, Brand brand, String searchKey);

    /**
     * 查询品牌分页列表
     *
     * @param pageDomain 分页数据
     * @param brand      分页对象
     * @return 品牌分页数据
     */
    PageResult<Brand> queryPage(PageDomain pageDomain, Brand brand);

    /**
     * 修改品牌状态
     *
     * @param brand 品牌,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    boolean updateDetailById(Brand brand);

    /**
     * 批量获取品牌信息
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true, 失败返回false
     */
    List<Brand> brandsInfo(List<Long> brandIds);
}
