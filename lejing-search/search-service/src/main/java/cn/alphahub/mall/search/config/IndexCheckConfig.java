package cn.alphahub.mall.search.config;

import cn.alphahub.mall.search.domain.SkuModel;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <b>SpringBoot启动时ES索引库检查</b>
 * <p>索引库不存在，创建索引库，以及映射关系</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/08
 */
@Slf4j
@Component
public class IndexCheckConfig implements ApplicationRunner {

    @Resource
    private ElasticsearchRestTemplate restTemplate;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String indexName;
        Map<String, Object> mapping;
        IndexOperations indexOps = restTemplate.indexOps(SkuModel.class);
        boolean exists = indexOps.exists();
        if (!exists) {
            // 创建索引
            indexOps.create();
            // 为该IndexOperations绑定到的实体创建索引映射
            Document document = indexOps.createMapping();
            // 将映射写入此IndexOperations绑定到的类的索引
            indexOps.putMapping();
            indexName = indexOps.getIndexCoordinates().getIndexName();
            mapping = indexOps.getMapping();
            System.out.println("\t已创建Elasticsearch索引:\n"
                    + "索引名称：" + indexName + "\n"
                    + "索引版本：" + document.getVersion() + "\n"
                    + "映射信息：\n"
                    + JSONUtil.toJsonStr(mapping));
        } else {
            indexName = indexOps.getIndexCoordinates().getIndexName();
            mapping = indexOps.getMapping();
            System.out.println("\tElasticsearch索引已存在:\n"
                    + "索引名称：" + indexName + "\n"
                    + "映射信息：\n"
                    + JSONUtil.toJsonStr(mapping));
        }
    }
}
