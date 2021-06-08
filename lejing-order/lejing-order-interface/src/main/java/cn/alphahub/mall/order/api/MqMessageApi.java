package cn.alphahub.mall.order.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.MqMessage;
import org.springframework.web.bind.annotation.*;

/**
 * MQ消息表-feign远程调用api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RequestMapping("order/mqmessage")
public interface MqMessageApi {

    /**
     * 查询MQ消息表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param mqMessage   MQ消息表, 查询字段选择性传入, 默认为等值查询
     * @return MQ消息表分页数据
     */
    @GetMapping("/list")
    BaseResult<PageResult<MqMessage>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MqMessage mqMessage
    );

    /**
     * 获取MQ消息表详情
     *
     * @param messageId MQ消息表主键id
     * @return MQ消息表详细信息
     */
    @GetMapping("/info/{messageId}")
    BaseResult<MqMessage> info(@PathVariable("messageId") String messageId);

    /**
     * 新增MQ消息表
     *
     * @param mqMessage MQ消息表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody MqMessage mqMessage);

    /**
     * 修改MQ消息表
     *
     * @param mqMessage MQ消息表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody MqMessage mqMessage);

    /**
     * 批量删除MQ消息表
     *
     * @param messageIds MQ消息表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{messageIds}")
    BaseResult<Boolean> delete(@PathVariable String[] messageIds);
}
