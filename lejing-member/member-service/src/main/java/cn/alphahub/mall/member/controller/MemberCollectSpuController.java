package cn.alphahub.mall.member.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.member.entity.MemberCollectSpuEntity;
import cn.alphahub.mall.member.service.MemberCollectSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员收藏的商品
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:20:49
 */
@RestController
@RequestMapping("member/membercollectspu")
public class MemberCollectSpuController {
    @Autowired
    private MemberCollectSpuService memberCollectSpuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:membercollectspu:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberCollectSpuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:membercollectspu:info")
    public R info(@PathVariable("id") Long id) {
        MemberCollectSpuEntity memberCollectSpu = memberCollectSpuService.getById(id);

        return R.ok().put("memberCollectSpu", memberCollectSpu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:membercollectspu:save")
    public R save(@RequestBody MemberCollectSpuEntity memberCollectSpu) {
        memberCollectSpuService.save(memberCollectSpu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:membercollectspu:update")
    public R update(@RequestBody MemberCollectSpuEntity memberCollectSpu) {
        memberCollectSpuService.updateById(memberCollectSpu);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("member:membercollectspu:delete")
    public R delete(@RequestBody Long[] ids) {
        memberCollectSpuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
