package cn.alphahub.mall.ware.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.dto.vo.FareVo;
import cn.alphahub.mall.ware.domain.WareInfo;
import cn.alphahub.mall.ware.service.WareInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 仓库信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController extends BaseController {
    @Resource
    private WareInfoService wareInfoService;

    /**
     * 获取运费信息
     *
     * @param addrId 收货地址id
     * @return 邮资
     */
    @GetMapping("/postage/info")
    public Result<FareVo> getPostageInfo(@RequestParam("addrId") Long addrId) {
        return Result.ok(wareInfoService.getPostageInfoByAddressId(addrId));
    }

    /**
     * 查询仓库信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param wareInfo    仓库信息, 查询字段选择性传入, 默认为等值查询
     * @param key         检索关键字
     * @return 仓库信息分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<WareInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "key", defaultValue = "") String key,
            WareInfo wareInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<WareInfo> pageResult = wareInfoService.queryPage(pageDomain, wareInfo, key);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取仓库信息详情
     *
     * @param id 仓库信息主键id
     * @return 仓库信息详细信息
     */
    @GetMapping("/info/{id}")
    public Result<WareInfo> info(@PathVariable("id") Long id) {
        WareInfo wareInfo = wareInfoService.getById(id);
        return ObjectUtils.anyNotNull(wareInfo) ? Result.ok(wareInfo) : Result.fail();
    }

    /**
     * 新增仓库信息
     *
     * @param wareInfo 仓库信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody WareInfo wareInfo) {
        boolean save = wareInfoService.save(wareInfo);
        return toOperationResult(save);
    }

    /**
     * 修改仓库信息
     *
     * @param wareInfo 仓库信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody WareInfo wareInfo) {
        boolean update = wareInfoService.updateById(wareInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除仓库信息
     *
     * @param ids 仓库信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = wareInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
