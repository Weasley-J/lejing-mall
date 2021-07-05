package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.dto.vo.FareVo;
import cn.alphahub.mall.ware.domain.WareInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 仓库信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareInfoService extends IService<WareInfo> {

    /**
     * 查询仓库信息分页列表
     *
     * @param pageDomain 分页数据
     * @param wareInfo   分页对象
     * @return 仓库信息分页数据
     */
    PageResult<WareInfo> queryPage(PageDomain pageDomain, WareInfo wareInfo);

    /**
     * 查询仓库信息列表
     *
     * @param wareInfo 仓库信息, 查询字段选择性传入, 默认为等值查询
     * @param key      检索关键字
     * @return 仓库信息分页数据
     */
    PageResult<WareInfo> queryPage(PageDomain pageDomain, WareInfo wareInfo, String key);

    /**
     * 获取运费信息
     *
     * @param addressId 收货地址id
     * @return 邮资
     */
    FareVo getPostageInfoByAddressId(Long addressId);
}
