package cn.alphahub.mall.search.repository;

import cn.alphahub.mall.search.domain.SkuModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * <b>商品-持久层接口</b>
 * <p>使用Spring Data Jpa的简便方式一样基于Elasticsearch开发业务</p>
 *
 * @author Weasley J
 * @date 2021年3月7日
 * @link https://docs.spring.io/spring-data/elasticsearch/docs/4.1.5/reference/html/#elasticsearch.query-methods.at-query
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<SkuModel, Long> {

}
