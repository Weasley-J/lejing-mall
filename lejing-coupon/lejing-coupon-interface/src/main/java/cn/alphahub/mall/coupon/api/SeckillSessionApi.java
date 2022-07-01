package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 秒杀活动场次 rpc api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SeckillSessionApi {

    /**
     * 获取最近3天的秒杀场次列表
     *
     * @return 最近3天的秒杀场次列表
     */
    @GetMapping("coupon/seckillsession/latest/3days/seckill/session")
    Result<List<SeckillSession>> getLatest3DaysSeckillSession();

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
    @GetMapping("coupon/seckillsession/list")
    Result<PageResult<SeckillSession>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSession seckillSession
    );

    /**
     * 查询秒杀活动场次列表
     *
     * @return 秒杀活动场次列表
     */
    @GetMapping("coupon/seckillsession/list/no/args")
    Result<PageResult<SeckillSession>> list();

    /**
     * 批量更新
     *
     * @param sessionList 列表
     * @return 提示
     */
    @PutMapping("coupon/seckillsession/batch/update")
    Result<Boolean> batchUpdate(@RequestBody  List<SeckillSession> sessionList);

    /**
     * 获取秒杀活动场次详情
     *
     * @param id 秒杀活动场次主键id
     * @return 秒杀活动场次详细信息
     */
    @GetMapping("coupon/seckillsession/info/{id}")
    Result<SeckillSession> info(@PathVariable("id") Long id);

    /**
     * 新增秒杀活动场次
     *
     * @param seckillSession 秒杀活动场次元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("coupon/seckillsession/save")
    Result<Boolean> save(@RequestBody SeckillSession seckillSession);

    /**
     * 修改秒杀活动场次
     *
     * @param seckillSession 秒杀活动场次, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("coupon/seckillsession/update")
    Result<Boolean> update(@RequestBody SeckillSession seckillSession);

    /**
     * 批量删除秒杀活动场次
     *
     * @param ids 秒杀活动场次id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("coupon/seckillsession/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
