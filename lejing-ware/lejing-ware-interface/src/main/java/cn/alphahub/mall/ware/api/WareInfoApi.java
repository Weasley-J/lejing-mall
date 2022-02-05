package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.dto.vo.FareVo;
import cn.alphahub.mall.ware.domain.WareInfo;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库信息-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareInfoApi {

    /**
     * 获取运费信息
     *
     * @param addrId 收货地址id
     * @return 邮资
     */
    @GetMapping("ware/wareinfo/postage/info")
    BaseResult<FareVo> getPostageInfo(@RequestParam("addrId") Long addrId);

    /**
     * 查询仓库信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param wareInfo    仓库信息, 查询字段选择性传入, 默认为等值查询
     * @return 仓库信息分页数据
     */
    @GetMapping("ware/wareinfo/list")
    BaseResult<PageResult<WareInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareInfo wareInfo
    );

    /**
     * 获取仓库信息详情
     *
     * @param id 仓库信息主键id
     * @return 仓库信息详细信息
     */
    @GetMapping("ware/wareinfo/info/{id}")
    BaseResult<WareInfo> info(@PathVariable("id") Long id);

    /**
     * 新增仓库信息
     *
     * @param wareInfo 仓库信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("ware/wareinfo/save")
    BaseResult<Boolean> save(@RequestBody WareInfo wareInfo);

    /**
     * 修改仓库信息
     *
     * @param wareInfo 仓库信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("ware/wareinfo/update")
    BaseResult<Boolean> update(@RequestBody WareInfo wareInfo);

    /**
     * 批量删除仓库信息
     *
     * @param ids 仓库信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("ware/wareinfo/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
