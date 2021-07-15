package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberCollectSpu;
import org.springframework.web.bind.annotation.*;

/**
 * 会员收藏的商品Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RequestMapping("member/membercollectspu")
public interface MemberCollectSpuApi {

    /**
     * 查询会员收藏的商品列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param memberCollectSpu 会员收藏的商品, 查询字段选择性传入, 默认为等值查询
     * @return 会员收藏的商品分页数据
     */
    @GetMapping("/list")
    BaseResult<PageResult<MemberCollectSpu>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberCollectSpu memberCollectSpu
    );

    /**
     * 获取会员收藏的商品详情
     *
     * @param id 会员收藏的商品主键id
     * @return 会员收藏的商品详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<MemberCollectSpu> info(@PathVariable("id") Long id);

    /**
     * 新增会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody MemberCollectSpu memberCollectSpu);

    /**
     * 修改会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody MemberCollectSpu memberCollectSpu);

    /**
     * 批量删除会员收藏的商品
     *
     * @param ids 会员收藏的商品id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
