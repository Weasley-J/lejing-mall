package cn.alphahub.mall.generator.config;


import cn.alphahub.mall.generator.entity.mongo.MongoDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz
 * @date 2020/5/10 12:05
 */
public class MongoManager {

    /**
     * mongo扫描很消耗性能 尤其是子类的封装  使用缓存
     */
    private static final Map<String, MongoDefinition> MONGO_CACHE = new ConcurrentHashMap<>();

    public static Map<String, MongoDefinition> getCache() {
        return MONGO_CACHE;
    }

    public static MongoDefinition getInfo(String tableName) {
        return MONGO_CACHE.getOrDefault(tableName, null);
    }

    public static MongoDefinition putInfo(String tableName, MongoDefinition mongoDefinition) {
        return MONGO_CACHE.put(tableName, mongoDefinition);
    }

    /**
     * 当前配置是否为mongo内容
     */
    public static boolean isMongo() {
        return DatabaseConfig.isMongo();
    }

}
