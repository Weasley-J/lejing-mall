package cn.alphahub.mall.seckill.service;

import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;

import java.util.List;

/**
 * 秒杀接口
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
public interface SeckillService {
    /**
     * 上架最近三天的秒杀商品
     * <p>
     * 不处理重复上架
     *     <ul>
     *         <li><img src='https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/QQ20210728224701.gif'/></li>
     *     </ul>
     * </p>
     */
    void onShelveSeckillSkuLatest3Days();

    /**
     * 获取当前时间参与秒杀的商品
     * <p>
     * <b style='color: red'>秒杀预览：</b>
     *     <ul>
     *         <li><img style='width: 130px; height: 100px' src='https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20210728223854713.png'/></li>
     *     </ul>
     * </p>
     *
     * @return 当前参与秒杀的商品
     */
    List<SeckillSkuRelation> getCurrentSeckillSkus();

    /**
     * 查询商品是否参加秒杀活动
     *
     * @param skuId 商品
     * @return 商品是否参加秒杀信息
     */
    SeckillSkuRelation getSkuSeckillInfoBySkuId(Long skuId);

    /**
     * 商品进行秒杀(秒杀开始)
     * <p>查看表: oms_order_item</p>
     *
     * @param killId 秒杀id
     * @param key    秒杀随机码
     * @param num    数量
     * @return 视图html
     * @throws InterruptedException 中断异常
     */
    String kill(String killId, String key, Integer num) throws InterruptedException;
}
