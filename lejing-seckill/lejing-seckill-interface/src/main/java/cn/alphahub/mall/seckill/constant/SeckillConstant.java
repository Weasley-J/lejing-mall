package cn.alphahub.mall.seckill.constant;

/**
 * 秒杀业务常量
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
public class SeckillConstant {
    /**
     * redis缓存前缀：场次信息
     */
    public static final String CACHE_PREFIX_SESSION = "lejing:seckill:sessions:";
    /**
     * redis缓存前缀：场次sku
     */
    public static final String CACHE_PREFIX_SKU = "lejing:seckill:skus:";
    /**
     * redis缓存前缀：sku秒杀信号量
     */
    public static final String CACHE_PREFIX_SKU_STOCK_SEMAPHORE = "lejing:seckill:stock:";
    /**
     * sku秒杀上架分布式锁前缀
     */
    public static final String CACHE_PREFIX_SKU_ON_SHELF_LOCK = "lejing:seckill:on_shelf:lock:";
}
