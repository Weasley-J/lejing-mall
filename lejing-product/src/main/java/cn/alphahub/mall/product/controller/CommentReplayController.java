package cn.alphahub.mall.product.controller;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.common.utils.R;
import cn.alphahub.mall.product.entity.CommentReplayEntity;
import cn.alphahub.mall.product.service.CommentReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 商品评价回复关系
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 04:31:16
 */
@RestController
@RequestMapping("product/commentreplay")
public class CommentReplayController {
    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:commentreplay:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentReplayService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("product:commentreplay:info")
    public R info(@PathVariable("id") Long id) {
        CommentReplayEntity commentReplay = commentReplayService.getById(id);

        return R.ok().put("commentReplay", commentReplay);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:commentreplay:save")
    public R save(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.save(commentReplay);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("product:commentreplay:update")
    public R update(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.updateById(commentReplay);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("product:commentreplay:delete")
    public R delete(@RequestBody Long[] ids) {
        commentReplayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
