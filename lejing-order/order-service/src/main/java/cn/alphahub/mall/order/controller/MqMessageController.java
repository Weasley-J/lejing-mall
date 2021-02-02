package cn.alphahub.mall.order.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.order.entity.MqMessageEntity;
import cn.alphahub.mall.order.service.MqMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
@RestController
@RequestMapping("order/mqmessage")
public class MqMessageController {
    @Autowired
    private MqMessageService mqMessageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:mqmessage:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = mqMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{messageId}")
    //@RequiresPermissions("order:mqmessage:info")
    public R info(@PathVariable("messageId") String messageId) {
        MqMessageEntity mqMessage = mqMessageService.getById(messageId);

        return R.ok().put("mqMessage", mqMessage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:mqmessage:save")
    public R save(@RequestBody MqMessageEntity mqMessage) {
        mqMessageService.save(mqMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:mqmessage:update")
    public R update(@RequestBody MqMessageEntity mqMessage) {
        mqMessageService.updateById(mqMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("order:mqmessage:delete")
    public R delete(@RequestBody String[] messageIds) {
        mqMessageService.removeByIds(Arrays.asList(messageIds));

        return R.ok();
    }

}
