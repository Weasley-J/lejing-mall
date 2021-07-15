package cn.alphahub.mall.ware.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 库存工作单Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareOrderTaskService extends IService<WareOrderTask> {

    /**
     * 查询库存工作单分页列表
     *
     * @param pageDomain    分页数据
     * @param wareOrderTask 分页对象
     * @return 库存工作单分页数据
     */
    PageResult<WareOrderTask> queryPage(PageDomain pageDomain, WareOrderTask wareOrderTask);

}
