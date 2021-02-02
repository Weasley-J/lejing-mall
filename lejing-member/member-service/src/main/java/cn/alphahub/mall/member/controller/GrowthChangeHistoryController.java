package cn.alphahub.mall.member.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.member.entity.GrowthChangeHistoryEntity;
import cn.alphahub.mall.member.service.GrowthChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 成长值变化历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:20:49
 */
@RestController
@RequestMapping("member/growthchangehistory")
public class GrowthChangeHistoryController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:growthchangehistory:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = growthChangeHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:growthchangehistory:info")
    public R info(@PathVariable("id") Long id) {
        GrowthChangeHistoryEntity growthChangeHistory = growthChangeHistoryService.getById(id);

        return R.ok().put("growthChangeHistory", growthChangeHistory);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:growthchangehistory:save")
    public R save(@RequestBody GrowthChangeHistoryEntity growthChangeHistory) {
        growthChangeHistoryService.save(growthChangeHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:growthchangehistory:update")
    public R update(@RequestBody GrowthChangeHistoryEntity growthChangeHistory) {
        growthChangeHistoryService.updateById(growthChangeHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("member:growthchangehistory:delete")
    public R delete(@RequestBody Long[] ids) {
        growthChangeHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
