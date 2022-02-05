package cn.alphahub.mall.search.repository;

import cn.alphahub.mall.search.domain.SkuModel;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Elasticsearch-增删改查测试类
 *
 * @author liuwenjing
 * @date 2021年3月7日
 */
@Slf4j
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Resource
    private ElasticsearchProperties properties;

    @BeforeEach
    void setUp() {
        System.out.println("-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("---------------------------");
    }

    /**
     * 创建索引库，以及映射
     */
    @Test
    void testCreateIndex() {
        /* 已过时 */
        // restTemplate.createIndex(SkuModel.class);
        // restTemplate.putMapping(SkuModel.class);

        // 通过restTemplate获取IndexOperations操作索引的实例
        IndexOperations indexOps = restTemplate.indexOps(SkuModel.class);
        // 为该IndexOperations绑定到的实体创建索引映射
        indexOps.createMapping();
        // 将映射写入此IndexOperations绑定到的类的索引
        indexOps.putMapping();
    }

    /**
     * 获取索引信息
     */
    @Test
    void testGetIndex() {
        // 此方法已过时
       /* Map<String, Object> mapping = restTemplate.getMapping(SkuModel.class);
        String prettyStr = JSONUtil.toJsonPrettyStr(mapping);
        System.out.println(prettyStr);
        System.out.println("--------------------------------------------------");*/
        // 通过restTemplate获取IndexOperations操作索引的实例
        IndexOperations indexOps = restTemplate.indexOps(SkuModel.class);
        Map<String, Object> mapping = indexOps.getMapping();
        String prettyStr = JSONUtil.toJsonPrettyStr(mapping);
        System.out.println(prettyStr);
        System.out.println();
        System.out.println(indexOps.getIndexCoordinates().getIndexName());
    }

    /**
     * 删除索引
     */
    @Test
    void testDelIndex() {
        // 通过restTemplate获取IndexOperations操作索引的实例
        IndexOperations indexOps = restTemplate.indexOps(SkuModel.class);
        String indexName = indexOps.getIndexCoordinates().getIndexName();
        log.info("索引名称：{}", indexName);
        boolean delete = indexOps.delete();
        log.info("删除结果：{}", delete);
    }

    /**
     * 索引是否存在，不存在会自动创建
     */
    @Test
    void indexExists() {
        IndexOperations indexOps = restTemplate.indexOps(SkuModel.class);
        boolean exists = indexOps.exists();
        System.out.println(exists);
    }

    @Test
    void findById() {
        Optional<SkuModel> skuModelOptional = productRepository.findById(1L);
        boolean present = skuModelOptional.isPresent();
        if (present) {
            SkuModel skuModel = skuModelOptional.get();
            System.out.println(JSONUtil.toJsonPrettyStr(skuModel));
        }
    }

    @Test
    void testResource() {
        List<String> uris = properties.getUris();
        System.out.println("uris = " + uris);
    }
}
