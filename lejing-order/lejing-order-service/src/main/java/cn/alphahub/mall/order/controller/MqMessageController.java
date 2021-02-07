package cn.alphahub.mall.order.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.domain.MqMessage;
import cn.alphahub.mall.order.service.MqMessageService;

import java.util.Arrays;

/**
 * MQ消息表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@RestController
@RequestMapping("order/mqmessage")
public class MqMessageController extends BaseController {
    @Autowired
    private MqMessageService mqMessageService;

    /**
     * 查询MQ消息表列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param mqMessage MQ消息表,字段选择性传入,默认为等值查询
     * @return MQ消息表分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:mqmessage:list")
    public BaseResult<PageResult<MqMessage>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MqMessage mqMessage
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MqMessage> pageResult = mqMessageService.queryPage(pageDomain, mqMessage);
        return (BaseResult<PageResult<MqMessage>>) toPageableResult(pageResult);
    }

    /**
     * 获取MQ消息表详情
     *
     * @param messageId MQ消息表主键id
     * @return MQ消息表详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("order:mqmessage:info")
    public BaseResult<MqMessage> info(@PathVariable("messageId") String messageId){
        MqMessage mqMessage = mqMessageService.getById(messageId);
        return (BaseResult<MqMessage>) toResponseResult(mqMessage);
    }

    /**
     * 新增MQ消息表
     *
     * @param mqMessage MQ消息表元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:mqmessage:save")
    public BaseResult<Boolean> save(@RequestBody MqMessage mqMessage) {
        boolean save = mqMessageService.save(mqMessage);
        return toOperationResult(save);
    }

    /**
     * 修改MQ消息表
     *
     * @param mqMessage MQ消息表,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:mqmessage:update")
    public BaseResult<Boolean> update(@RequestBody MqMessage mqMessage) {
        boolean update = mqMessageService.updateById(mqMessage);
        return toOperationResult(update);
    }

    /**
     * 批量删除MQ消息表
     *
     * @param messageIds MQ消息表id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("order:mqmessage:delete")
    public BaseResult<Boolean> delete(@PathVariable String[] messageIds){
        boolean delete = mqMessageService.removeByIds(Arrays.asList(messageIds));
        return toOperationResult(delete);
    }
}
