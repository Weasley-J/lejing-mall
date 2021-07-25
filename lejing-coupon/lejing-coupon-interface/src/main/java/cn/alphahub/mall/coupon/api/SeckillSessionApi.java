package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀活动场次 rpc api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RequestMapping("coupon/seckillsession")
public interface SeckillSessionApi {

    /**
     * 获取最近3天的秒杀场次列表
     *
     * @return 最近3天的秒杀场次列表
     */
    @GetMapping("latest/3days/seckill/session")
    BaseResult<List<SeckillSession>> getLatest3DaysSeckillSession();

    /**
     * 查询秒杀活动场次列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param seckillSession 秒杀活动场次, 查询字段选择性传入, 默认为等值查询
     * @return 秒杀活动场次分页数据
     */
    @GetMapping("/list")
    BaseResult<PageResult<SeckillSession>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSession seckillSession
    );

    /**
     * 获取秒杀活动场次详情
     *
     * @param id 秒杀活动场次主键id
     * @return 秒杀活动场次详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<SeckillSession> info(@PathVariable("id") Long id);

    /**
     * 新增秒杀活动场次
     *
     * @param seckillSession 秒杀活动场次元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody SeckillSession seckillSession);

    /**
     * 修改秒杀活动场次
     *
     * @param seckillSession 秒杀活动场次, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody SeckillSession seckillSession);

    /**
     * 批量删除秒杀活动场次
     *
     * @param ids 秒杀活动场次id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
